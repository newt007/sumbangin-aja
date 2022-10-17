package com.bintangpoetra.sumbanginaja.data.auth.remote

import com.bintangpoetra.sumbanginaja.data.auth.model.UserItem
import com.bintangpoetra.sumbanginaja.data.lib.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    @FormUrlEncoded
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponse<UserItem>

    @POST("register")
    suspend fun registerUser(): BaseResponse<UserItem>

}