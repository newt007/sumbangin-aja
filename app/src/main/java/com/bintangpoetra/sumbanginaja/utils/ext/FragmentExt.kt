package com.bintangpoetra.sumbanginaja.utils.ext

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.presentation.dialog.BarcodeDialogFragment
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_FOOD_CODE
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_RAW_STRING

fun Fragment.showBarcodeDialog(foodCode: String) {
    val fragmentManager = parentFragmentManager
    val dialogFragment = BarcodeDialogFragment.newInstance()

    val args = Bundle()

    with(args) {
        args.putString(KEY_RAW_STRING, foodCode)
        dialogFragment.arguments = this
    }

    dialogFragment.show(fragmentManager, BarcodeDialogFragment::class.java.simpleName)

}