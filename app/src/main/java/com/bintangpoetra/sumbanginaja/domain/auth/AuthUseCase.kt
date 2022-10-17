package com.bintangpoetra.sumbanginaja.domain.auth

import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun loginUser(email: String, password: String): Flow<ApiResponse<User>>

    fun registerUser(): Flow<ApiResponse<User>>

}