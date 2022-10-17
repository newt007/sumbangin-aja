package com.bintangpoetra.sumbanginaja.data.auth.model

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("profile_users")
    val profileUsers: String?,
    @SerializedName("no_handphone")
    val phoneNumber: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("token")
    val token: String?
)
