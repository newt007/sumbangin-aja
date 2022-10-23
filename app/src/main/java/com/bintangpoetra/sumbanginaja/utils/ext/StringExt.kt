package com.bintangpoetra.sumbanginaja.utils.ext

import android.text.TextUtils
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


fun emptyString(): String = ""

fun String.isEmailValid(): Boolean  {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.toBearer(): String {
    return "Bearer $this"
}

fun String.toRequestBody(): RequestBody {
    return this.toRequestBody("multipart/form-data".toMediaTypeOrNull())
}

fun String.isTheFoodOwner(id: String): Boolean {
    return this == id
}

fun Food.generateMessageToFoodRanger(): String {
    val user = this.user
    val food = this
    return "Hallo kak ${user?.name}, apakah makanan ${food.name} pada aplikasi SumbanginAja masih tersedia ? " +
            "Saya tertarik untuk mengambil makanan tersebut apabila makanannya masih tersedia"
}

fun String.toWhatsAppNumberFormat(): String {
    return "+62" + this.substring(0, this.length)
}