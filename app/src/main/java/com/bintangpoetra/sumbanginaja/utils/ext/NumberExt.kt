package com.bintangpoetra.sumbanginaja.utils.ext

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
