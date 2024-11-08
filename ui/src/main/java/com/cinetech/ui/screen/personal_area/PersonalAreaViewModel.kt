package com.cinetech.ui.screen.personal_area

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cinetech.domain.model.Avatar
import com.cinetech.domain.model.User
import com.cinetech.domain.repository.UserLocalRepository
import com.cinetech.domain.repository.UserRepository
import com.cinetech.domain.utils.Response
import com.cinetech.ui.base.BaseViewModel
import com.cinetech.ui.core.formatMillisToISO8601
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiEffect
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiEvent
import com.cinetech.ui.screen.personal_area.model.PersonalAreaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class PersonalAreaViewModel @Inject constructor(
    private val userLocalRepository: UserLocalRepository,
    private val userRemoteRepository: UserRepository
) : BaseViewModel<PersonalAreaUiState, PersonalAreaUiEvent, PersonalAreaUiEffect>(
    initialState = PersonalAreaUiState(),
    reducer = PersonalAreaReducer(),
) {

    init {
        loadUserData()
    }

    fun enableEditMode() {
        sendEvent(PersonalAreaUiEvent.UpdateEditMode(true))
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.user?.copy()))
        sendEvent(PersonalAreaUiEvent.UpdateEditUserImage(state.value.userImage))
    }

    fun disableEditMode() {
        sendEvent(PersonalAreaUiEvent.UpdateEditMode(false))
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
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.editUser?.copy(birthday = formatMillisToISO8601(millis))))
    }

    fun onAboutChange(newValue: String) {
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.editUser?.copy(status = newValue)))
    }

    fun onCityChange(newValue: String) {
        sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.editUser?.copy(city = newValue)))
    }

    fun onImageSelect(uri: Uri?, context: Context) {
        if (uri == null) return

        Glide.with(context)
            .asBitmap()
            .load(uri)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {


                    val byteArrayOutputStream = ByteArrayOutputStream()
                    resource.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    val imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)

                    val avatar = Avatar(
                        avatar = getFileNameFromUri(context,uri),
                        bigAvatar = imageBase64,
                        miniAvatar = imageBase64
                    )

                    sendEvent(PersonalAreaUiEvent.UpdateEditUserData(state.value.editUser?.copy(avatars = avatar)))
                    sendEvent(PersonalAreaUiEvent.UpdateEditUserImage(resource.asImageBitmap()))
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }

    fun saveData(){
        if(state.value.user == state.value.editUser) return

        println(state.value.editUser)
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val userLocal = userLocalRepository.getUser()
            if (userLocal == null) {
                getUSerDataFromServer()
                return@launch
            }
            sendEvent(PersonalAreaUiEvent.UpdateUserData(userLocal))
            sendEvent(PersonalAreaUiEvent.UpdateUserImage(base64ToBitmap(userLocal.avatars?.bigAvatar)?.asImageBitmap()))
            sendEvent(PersonalAreaUiEvent.UpdateLoading(false))
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

    private fun getUSerDataFromServer() {
        viewModelScope.launch {
            userRemoteRepository.getUser().collect { response ->
                when (response) {
                    is Response.Error -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoading(false))
                    }

                    Response.Loading -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoading(true))
                    }

                    is Response.Success -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoading(false))
                        sendEvent(PersonalAreaUiEvent.UpdateUserData(response.result))
                        sendEvent(PersonalAreaUiEvent.UpdateUserImage(base64ToBitmap(response.result.avatars?.bigAvatar)?.asImageBitmap()))
                        saveUserDataToLocalStore(response.result)
                    }

                    Response.Timeout -> {
                        sendEvent(PersonalAreaUiEvent.UpdateLoading(false))
                    }
                }
            }
        }
    }

    private fun saveUserDataToLocalStore(user: User) {
        viewModelScope.launch {
            userLocalRepository.save(user)
        }
    }

    private fun base64ToBitmap(base64String: String?): Bitmap? {
        if (base64String == null) return null
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            null
        }
    }


}