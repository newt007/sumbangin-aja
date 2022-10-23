package com.bintangpoetra.sumbanginaja.utils.ext

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showConfirmDialog(onPositiveClick:() -> Unit){
    AlertDialog.Builder(requireContext()).apply {
        setTitle(getString(R.string.title_confirmation))
        setMessage(getString(R.string.message_logout))
        setNegativeButton("Batal") { p0, _ ->
            p0.dismiss()
        }
        setPositiveButton("Ya") { _, _ ->
            onPositiveClick()
        }
    }.create().show()
}

fun Fragment.getPrefManager(): PreferenceManager {
    return PreferenceManager(this.requireContext())
}