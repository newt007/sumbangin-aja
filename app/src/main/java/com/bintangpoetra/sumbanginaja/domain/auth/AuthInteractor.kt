package com.bintangpoetra.sumbanginaja.domain.auth

import com.bintangpoetra.sumbanginaja.data.auth.AuthRepository
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class AuthInteractor(
    private val repository: AuthRepository
): AuthUseCase {
    override fun loginUser(email: String, password: String): Flow<ApiResponse<User>> {
        return repository.loginUser(email, password)
            .flowOn(Dispatchers.IO)
    }

    override fun registerUser(): Flow<ApiResponse<User>> {
        return repository.registerUser()
            .flowOn(Dispatchers.IO)
    }
}