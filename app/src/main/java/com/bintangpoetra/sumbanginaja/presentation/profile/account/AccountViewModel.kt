package com.bintangpoetra.sumbanginaja.presentation.profile.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.AuthUseCase
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import kotlinx.coroutines.launch

class AccountViewModel(
    private val authUseCase: AuthUseCase
): ViewModel() {

    val profileDetailResult: LiveData<ApiResponse<User>> by lazy { _profileDetailResult }
    private val _profileDetailResult = MutableLiveData<ApiResponse<User>>()

    val updateProfileResult: LiveData<ApiResponse<User>> by lazy { _updateProfileResult }
    private val _updateProfileResult = MutableLiveData<ApiResponse<User>>()

    fun getProfileDetail() {
        viewModelScope.launch {
            authUseCase.getProfileDetail()
                .collect {
                    _profileDetailResult.value = it
                }
        }
    }

    fun updateProfile(name: String, address: String) {
        viewModelScope.launch {
            authUseCase.updateProfile(name, address)
                .collect {
                    _updateProfileResult.value = it
                }
        }
    }

}