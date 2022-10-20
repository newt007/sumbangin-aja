package com.bintangpoetra.sumbanginaja.data.lib

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T
)

data class AlternateBaseResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
)