package com.bintangpoetra.sumbanginaja.domain.auth

import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun loginUser(email: String, password: String): Flow<ApiResponse<User>>

    fun registerUser(
        name: String,
        email: String,
        password: String,
        type: String
    ): Flow<ApiResponse<String>>

    fun getProfileDetail(): Flow<ApiResponse<User>>

    fun updateProfile(name: String, address: String): Flow<ApiResponse<User>>

}