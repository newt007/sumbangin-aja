package com.bintangpoetra.sumbanginaja.data.auth

import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.domain.auth.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(email: String, password: String): Flow<ApiResponse<User>>

    fun registerUser(
        name: String,
        email: String,
        password: String,
        type: String
    ): Flow<ApiResponse<String>>

    fun getProfileDetail(): Flow<ApiResponse<User>>

    fun updateProfile(
        name: String,
        address: String,
        phoneNumber: String
    ): Flow<ApiResponse<User>>

    fun logout(): Flow<ApiResponse<String>>

}