package com.bintangpoetra.sumbanginaja.utils.ext

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlin.system.exitProcess

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showConfirmDialog(onPositiveClick: Unit){
    AlertDialog.Builder(requireContext()).apply {
        setTitle(getString(R.string.title_confirmation))
        setMessage(getString(R.string.message_logout))
        setNegativeButton("Batal") { p0, _ ->
            p0.dismiss()
        }
        setPositiveButton("Ya") { _, _ ->
            onPositiveClick
        }
    }.create().show()
}

