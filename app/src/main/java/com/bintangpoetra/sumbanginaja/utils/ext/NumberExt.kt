package com.bintangpoetra.sumbanginaja.utils.ext

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Double?.orDefaultLatitude(): Double {
    return this ?: -7.913652682749895
}

fun Double?.orDefaultLongitude(): Double {
    return this ?: 113.8221190603561
}

fun Int?.toDistance(): String {
    if ((this ?: 0) > 1000) {
        return "${this?.div(1000) ?: 0} km"
    }

    return "$this km"
}

fun Int.toRequestBody(): RequestBody {
    return this.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
}

fun Double.toRequestBody(): RequestBody {
    return this.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
}
