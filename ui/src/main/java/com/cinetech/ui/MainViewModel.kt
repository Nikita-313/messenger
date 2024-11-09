package com.cinetech.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cinetech.domain.repository.JwtTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository
) : ViewModel() {

    private val _isReady = mutableStateOf(false)
    val isReady = _isReady as State<Boolean>
    private val _isAuth = mutableStateOf(false)
    val isAuth = _isAuth as State<Boolean>

    init {
        viewModelScope.launch {
            _isAuth.value = jwtTokenRepository.getToken() != null
            _isReady.value = true
        }
    }
}