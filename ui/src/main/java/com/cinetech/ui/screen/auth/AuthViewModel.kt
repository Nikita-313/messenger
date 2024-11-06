package com.cinetech.ui.screen.auth

import androidx.core.text.isDigitsOnly
import com.cinetech.ui.base.BaseViewModel
import com.cinetech.ui.screen.auth.model.AuthUiEffect
import com.cinetech.ui.screen.auth.model.AuthUiEvent
import com.cinetech.ui.screen.auth.model.AuthUiState
import com.cinetech.ui.screen.auth.model.Country
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale
import javax.inject.Inject

class AuthViewModel @Inject constructor() : BaseViewModel<AuthUiState, AuthUiEvent, AuthUiEffect>(
    initialState = AuthUiState(),
    reducer = AuthUiReducer(),
) {
    val countries = mutableMapOf<String, Country>()

    init {
        initCountries()
        setDefaultCountry()
    }

    fun onSelectCountry(country: Country){
        sendEvent(AuthUiEvent.OnCountryCodeTextChange(country.phoneCode, country))
    }

    fun onCountyCodeChange(newValue: String) {
        if (newValue.length == 1 && newValue.first() == '0') return
        if (newValue.isDigitsOnly() && newValue.length <= 3) {
            val country = if (newValue.isNotEmpty()) getCountryNameByCountryCode(newValue.toInt()) else null
            sendEvent(AuthUiEvent.OnCountryCodeTextChange(newValue, countries[country]))
        }
    }

    fun onPhoneNumberChange(newValue: String) {
        if (newValue.isDigitsOnly() && newValue.length <= 10) {
            sendEvent(AuthUiEvent.OnPhoneNumberTextChange(newValue))
        }
    }

    fun sendSmsCode(){
        if(!checkPhoneNumberValid()) {
            sendEffect(AuthUiEffect.PhoneNumberInvalid)
            return
        }


    }

    private fun checkPhoneNumberValid():Boolean{
        if(state.value.phoneNumber.length < 6) return false
        if(state.value.countyCode.isEmpty()) return false
        return true
    }

    private fun setDefaultCountry() {
        val country = countries[Locale.getDefault().country]
        if (country != null){
            sendEvent(AuthUiEvent.OnCountryCodeTextChange(country.phoneCode, country))
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