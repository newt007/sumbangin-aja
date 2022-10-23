package com.bintangpoetra.sumbanginaja.utils.ext

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.presentation.dialog.BarcodeDialogFragment
import com.bintangpoetra.sumbanginaja.presentation.dialog.CustomDialogFragment
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_DESC
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_FOOD_CODE
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_IMAGE_RES
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_ON_CLICK
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_RAW_STRING
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_TEXT
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_TITLE
import java.io.Serializable

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

fun Fragment.showCustomDialog(
    @DrawableRes image: Int,
    title: String,
    description: String,
    btnText: String,
    onBtnClick: () -> Unit
) {
    val fragmentManager = parentFragmentManager
    val dialogFragment = CustomDialogFragment.newInstance(image, title, description, btnText, onBtnClick)

    val args = Bundle()
    with(args) {
        args.putInt(KEY_IMAGE_RES, image)
        args.putString(KEY_TITLE, title)
        args.putString(KEY_DESC, description)
        args.putString(KEY_TEXT, btnText)
        args.putSerializable(KEY_ON_CLICK, onBtnClick as Serializable)
        dialogFragment.arguments = this
    }

    dialogFragment.show(fragmentManager, CustomDialogFragment::class.java.simpleName)
}