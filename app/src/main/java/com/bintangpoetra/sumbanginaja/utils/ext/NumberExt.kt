package com.bintangpoetra.sumbanginaja.utils.ext

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}