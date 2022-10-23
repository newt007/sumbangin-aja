package com.bintangpoetra.sumbanginaja.domain.food.model

import com.bintangpoetra.sumbanginaja.data.auth.model.UserItem
import com.bintangpoetra.sumbanginaja.utils.ext.emptyString
import com.google.gson.annotations.SerializedName

data class Food(
    val id: Int = 0,
    val userID: Int = 0,
    val name: String = emptyString(),
    val images: String = emptyString(),
    val descriptions: String = emptyString(),
    val status: String = emptyString(),
    val foodGenerateCode: String = emptyString(),
    val paybackTime: String = emptyString(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val createdAt: String = emptyString(),
    val updatedAt: String = emptyString(),
    val user: UserItem?
)
