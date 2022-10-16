package com.bintangpoetra.sumbanginaja.utils.ext

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.disable() {
    isEnabled = false
}

fun View.enabled() {
    isEnabled = true
}

infix fun View.click(click: () -> Unit) {
    setOnClickListener { click() }
}