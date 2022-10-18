package com.bintangpoetra.sumbanginaja.utils.ext

import android.view.View
import android.widget.EditText
import android.widget.ProgressBar

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

fun EditText.showError(message: String) {
    error = message
    requestFocus()
}

fun showLoading(loadingView: View, view: View) {
    loadingView.show()
    view.show()
}

fun hideLoading(loadingView: View, view: View) {
    loadingView.gone()
    view.gone()
}