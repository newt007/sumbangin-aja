package com.bintangpoetra.sumbanginaja.utils.ext

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Int?.toDistance(): String {
    if ((this ?: 0) > 1000) {
        return "${this?.div(1000) ?: 0} km"
    }

    return "$this km"
}
