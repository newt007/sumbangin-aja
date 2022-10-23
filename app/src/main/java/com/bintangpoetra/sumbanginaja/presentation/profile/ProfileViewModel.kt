package com.bintangpoetra.sumbanginaja.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.AuthUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authUseCase: AuthUseCase
): ViewModel() {

    val logoutResult: LiveData<ApiResponse<String>> by lazy { _logoutResult }
    private val _logoutResult = MutableLiveData<ApiResponse<String>>()

    fun logout() {
        viewModelScope.launch {
            authUseCase.logout()
                .collect {
                    _logoutResult.postValue(it)
                }
        }
    }

}