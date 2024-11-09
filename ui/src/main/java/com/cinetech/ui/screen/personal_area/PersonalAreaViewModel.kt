package com.cinetech.ui.screen.personal_area

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import com.cinetech.domain.model.AvatarData
import com.cinetech.domain.model.UpdateUserData
import com.cinetech.domain.model.User
import com.cinetech.domain.repository.AvatarRepository
import com.cinetech.domain.repository.UserLocalRepository
import com.cinetech.domain.repository.UserRepository
import com.cinetech.domain.utils.Response
import com.cinetech.ui.R
import com.cinetech.ui.base.BaseAndroidViewModel
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiEffect
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiEvent
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiState
import com.cinetech.ui.screen.personal_area.utils.millisToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class PersonalAreaViewModel @Inject constructor(
    application: Application,
    private val userLocalRepository: UserLocalRepository,
    private val userRemoteRepository: UserRepository,
    private val avatarRepository: AvatarRepository
) : BaseAndroidViewModel<PersonalAreaUiState, PersonalAreaUiEvent, PersonalAreaUiEffect>(
    initialState = PersonalAreaUiState(),
    reducer = PersonalAreaReducer(),
    application = application
) {

    private var localAvatarData: AvatarData? = null

    init {
        loadUserData()
    }

    fun enableEditMode() {
        sendEvent(PersonalAreaUiEvent.UpdateEditMode(true))
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.user?.copy()))
        sendEvent(PersonalAreaUiEvent.UpdateUserAvatarData(null))
        sendEvent(PersonalAreaUiEvent.UpdateUserNewImage(state.value.userImage))
    }

    fun canselEditMode() {
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.user?.copy()))
        sendEvent(PersonalAreaUiEvent.UpdateUserAvatarData(null))
        sendEvent(PersonalAreaUiEvent.UpdateEditMode(false))
        sendEvent(PersonalAreaUiEvent.UpdateUserNewImage(null))
    }

    fun showDatePiker() {
        sendEvent(PersonalAreaUiEvent.ShowDatePiker(true))
    }

    fun dismissDatePiker() {
        sendEvent(PersonalAreaUiEvent.ShowDatePiker(false))
    }

    fun onDateSelected(millis: Long?) {
        sendEvent(PersonalAreaUiEvent.ShowDatePiker(false))
        if (millis == null) return
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.editUser?.copy(birthday = millisToDate(millis))))
    }

    fun onAboutChange(newValue: String) {
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.editUser?.copy(status = newValue)))
    }

    fun onCityChange(newValue: String) {
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.editUser?.copy(city = newValue)))
    }

    fun onImageSelect(uri: Uri?) {
        if (uri == null) return

        viewModelScope.launch {
            val loader = ImageLoader(getApplication())
            val request = ImageRequest.Builder(getApplication() as Context)
                .data(uri)
                .build()

            val result = loader.execute(request)
            if (result is SuccessResult) {
                val resource = result.image.toBitmap()
                val imageBase64 = bitmapToBase64(resource, Bitmap.CompressFormat.JPEG, 70)
                val updateAvatar = AvatarData(
                    filename = getFileNameFromUri(getApplication(), uri) ?: "",
                    base64 = imageBase64,
                )
                sendEvent(PersonalAreaUiEvent.UpdateUserAvatarData(updateAvatar))
                sendEvent(PersonalAreaUiEvent.UpdateUserNewImage(resource.asImageBitmap()))
            }
        }
    }

    fun saveData() {
        val oldUserData = state.value.user
        val newUserData = state.value.editUser
        val newUserImage = state.value.updateAvatarData

        if (oldUserData == newUserData && newUserImage == null) {
            canselEditMode()
            return
        }

        if (newUserData == null) {
            canselEditMode()
            return
        }

        val image = newUserImage ?: localAvatarData

        val updateUserData = UpdateUserData(
            name = newUserData.name,
            username = newUserData.username,
            birthday = newUserData.birthday,
            city = newUserData.city,
            vk = newUserData.vk,
            instagram = newUserData.instagram,
            status = newUserData.status,
            avatar = image,
        )

        viewModelScope.launch {
            userRemoteRepository.updateUser(updateUserData).collect { response ->
                when (response) {
                    is Response.Error -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingNewData(false))
                        sendEffect(PersonalAreaUiEffect.ShowToast(R.string.personal_area_screen_update_error))
                    }

                    Response.Loading -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingNewData(true))
                    }

                    is Response.Success -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingNewData(false))
                        val user = newUserData.copy(avatars = response.result)
                        saveNewUserData(user,image)
                        sendEvent(PersonalAreaUiEvent.UpdateUserData(user))
                        sendEvent(PersonalAreaUiEvent.UpdateUserImage(state.value.newUserImage))
                        sendEvent(PersonalAreaUiEvent.UpdateEditMode(false))
                    }

                    Response.Timeout -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingNewData(false))
                        sendEffect(PersonalAreaUiEffect.ShowToast(R.string.personal_area_screen_update_error))
                    }
                }
            }
        }
    }

    fun getUserDataFromServer() {
        viewModelScope.launch {
            userRemoteRepository.getUser().collect { response ->
                when (response) {
                    is Response.Error -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingGetData(false))
                        sendEvent(PersonalAreaUiEvent.UpdateErrorGetData(true))
                    }

                    Response.Loading -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingGetData(true))
                        sendEvent(PersonalAreaUiEvent.UpdateErrorGetData(false))
                    }

                    is Response.Success -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingGetData(false))
                        loadAvatarFromServer(response.result.avatars?.bigAvatar,response.result.avatar)
                        sendEvent(PersonalAreaUiEvent.UpdateUserData(response.result))
                        saveUserData(response.result)
                    }

                    Response.Timeout -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoadingGetData(false))
                        sendEvent(PersonalAreaUiEvent.UpdateErrorGetData(true))
                    }
                }
            }
        }
    }

    private fun loadAvatarFromServer(url: String?, filename: String?) {
        if (url == null) return
        viewModelScope.launch {
            val loader = ImageLoader(getApplication())
            val request = ImageRequest.Builder(getApplication() as Context)
                .data(IMAGE_LOAD_URL + url)
                .build()

            val result = loader.execute(request)
            if (result is SuccessResult) {
                val bitmap = result.image.toBitmap()
                sendEvent(PersonalAreaUiEvent.UpdateUserImage(bitmap.asImageBitmap()))
                val avatarData = AvatarData(
                    filename = filename ?: "",
                    base64 = bitmapToBase64(bitmap)
                )
                localAvatarData = avatarData
                avatarRepository.save(avatarData)
            }
        }
    }

    private fun saveNewUserData(user: User,avatar:AvatarData?) {
        viewModelScope.launch {
            userLocalRepository.save(user)
            if(avatar == null) avatarRepository.delete()
            else avatarRepository.save(avatar)
            localAvatarData = avatar
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val userLocal = userLocalRepository.getUser()
            if (userLocal == null) {
                 getUserDataFromServer()
                return@launch
            }
            localAvatarData = avatarRepository.getAvatar()
            sendEvent(PersonalAreaUiEvent.UpdateUserImage(base64ToBitmap(localAvatarData?.base64)?.asImageBitmap()))
            sendEvent(PersonalAreaUiEvent.UpdateUserData(userLocal))
        }
    }

    private fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)

        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                it.moveToFirst()
                fileName = it.getString(nameIndex)
            }
        }
        return fileName
    }

    private fun saveUserData(user: User) {
        viewModelScope.launch {
            userLocalRepository.save(user)
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, quality: Int = 100): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(format, quality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun base64ToBitmap(base64: String?): Bitmap? {
        if (base64 == null) return null
        return try {
            val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        const val IMAGE_LOAD_URL = "https://plannerok.ru/"
    }
}