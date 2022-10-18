package com.bintangpoetra.sumbanginaja.presentation.auth.login

import androidx.lifecycle.*
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.AuthUseCase
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    val loginUserResult: LiveData<ApiResponse<User>> by lazy { _loginUserResult }
    private val _loginUserResult = MutableLiveData<ApiResponse<User>>()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.loginUser(email, password)
                .collect {
                    _loginUserResult.value = it
                }
        }
    }

}