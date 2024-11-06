package com.cinetech.ui.screen.auth

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.cinetech.domain.exeption.ValidationException
import com.cinetech.domain.repository.AuthRepository
import com.cinetech.domain.utils.Response
import com.cinetech.ui.R
import com.cinetech.ui.base.BaseViewModel
import com.cinetech.ui.navigation.Screen
import com.cinetech.ui.screen.auth.model.AuthUiEffect
import com.cinetech.ui.screen.auth.model.AuthUiEvent
import com.cinetech.ui.screen.auth.model.AuthUiState
import com.cinetech.ui.screen.auth.model.Country
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel<AuthUiState, AuthUiEvent, AuthUiEffect>(
    initialState = AuthUiState(),
    reducer = AuthUiReducer(),
) {
    val countries = mutableMapOf<String, Country>()

    init {
        initCountries()
        setDefaultCountry()
    }

    fun onSelectCountry(country: Country) {
        sendEvent(AuthUiEvent.OnCountryCodeTextChange(TextFieldValue(country.phoneCode), country))
    }

    fun onCountyCodeChange(textFieldValue: TextFieldValue) {
        val newValue = textFieldValue.text
        if (state.value.isLoading) return
        if (newValue.length == 1 && newValue.first() == '0') return
        if (newValue.isDigitsOnly() && newValue.length <= 3) {
            val country = if (newValue.isNotEmpty()) getCountryNameByCountryCode(newValue.toInt()) else null
            sendEvent(AuthUiEvent.OnCountryCodeTextChange(textFieldValue, countries[country]))
        }
    }

    fun onPhoneNumberChange(textFieldValue: TextFieldValue) {
        val newValue = textFieldValue.text
        if (state.value.isLoading) return
        if (newValue.isDigitsOnly() && newValue.length <= 10) {
            sendEvent(AuthUiEvent.OnPhoneNumberTextChange(textFieldValue))
        }
    }

    fun sendSmsCode() {
        if (!checkPhoneNumberValid()) {
            sendEffect(AuthUiEffect.PhoneNumberInvalid)
            return
        }

        val phone = "+" + state.value.countyCode.text + state.value.phoneNumber.text

        viewModelScope.launch {
            authRepository.sendAuthCode(phone).collect { response ->
                when (response) {
                    is Response.Error -> {
                        sendEvent(AuthUiEvent.Loading(false))
                        errorHandler(response.throwable)
                    }

                    Response.Loading -> {
                        sendEvent(AuthUiEvent.Loading(true))
                    }

                    is Response.Success -> {
                        sendEvent(AuthUiEvent.Loading(false))
                        sendEffect(AuthUiEffect.NavigateTo(Screen.SmsVerification(phone)))
                    }

                    Response.Timeout -> {
                        sendEvent(AuthUiEvent.Loading(false))
                    }
                }
            }
        }

    }

    private fun errorHandler(throwable: Throwable?) {
        if (throwable == null) return
        Log.e("AuthViewModel errorHandler", throwable.stackTraceToString())
        when (throwable) {
            is UnknownHostException -> {
                sendEventForEffect(AuthUiEvent.ShowError(R.string.exception_no_internet_connection))
            }
            is ValidationException -> {
                sendEventForEffect(AuthUiEvent.ShowError(R.string.exception_validation_error))
            }
            else -> {
                sendEventForEffect(AuthUiEvent.ShowError(R.string.exception_unknown))
            }
        }
    }

    private fun checkPhoneNumberValid(): Boolean {
        if (state.value.phoneNumber.text.length < 6) return false
        if (state.value.countyCode.text.isEmpty()) return false
        return true
    }

    private fun setDefaultCountry() {
        val country = countries[Locale.getDefault().country]
        if (country != null) {
            sendEvent(AuthUiEvent.OnCountryCodeTextChange(TextFieldValue(country.phoneCode), country))
        }
    }

    private fun initCountries() {
        val list = Locale.getISOCountries()
        for (i in list.indices) {
            val locale = Locale("", list[i])
            val countyPhoneCode = getCountryCodeByCountryName(locale.country)
            if (countyPhoneCode != null) {
                countries[locale.country] = Country(
                    phoneCode = countyPhoneCode,
                    name = locale.displayCountry,
                    isoName = locale.country
                )
            }
        }
    }

    private fun getCountryCodeByCountryName(countryName: String): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val countryCodePhone = phoneUtil.getCountryCodeForRegion(countryName)
            if (countryCodePhone == 0) return null
            countryCodePhone.toString()
        } catch (e: Exception) {
            null
        }
    }

    private fun getCountryNameByCountryCode(countryCode: Int): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            phoneUtil.getRegionCodeForCountryCode(countryCode)
        } catch (e: Exception) {
            null
        }
    }
}