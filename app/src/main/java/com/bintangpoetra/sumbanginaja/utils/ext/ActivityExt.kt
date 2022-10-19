package com.bintangpoetra.sumbanginaja.utils.ext

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

fun Activity.showExitaAppDialog(){
    AlertDialog.Builder(this).apply {
        setTitle("Keluar Aplikasi")
        setMessage("Apakah Anda yakin untuk keluar dari aplikasi SumbanginAja?")
        setNegativeButton("Tidak") { p0, _ ->
            p0.dismiss()
        }
        setPositiveButton("IYA") { _, _ ->
            finish()
            exitProcess(0)
        }
    }.create().show()
}