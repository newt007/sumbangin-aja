package com.bintangpoetra.sumbanginaja.data.food.model

import com.bintangpoetra.sumbanginaja.data.auth.model.UserItem
import com.google.gson.annotations.SerializedName

data class FoodItem (
    val id: Int?,
    @SerializedName("user_id")
    val userID: Int?,
    val name: String?,
    val images: String?,
    val descriptions: String?,
    @SerializedName("payback_time")
    val paybackTime: String?,
    val latitude: Double?,
    val longitude: Double?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val user: UserItem?
)
