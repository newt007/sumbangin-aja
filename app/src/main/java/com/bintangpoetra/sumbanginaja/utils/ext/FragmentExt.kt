package com.bintangpoetra.sumbanginaja.utils.ext

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.presentation.dialog.BarcodeDialogFragment
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

//fun Fragment.showCustomDialog(
//    @DrawableRes image: Int,
//    title: String,
//    description: String,
//    btnText: String,
//    onBtnClick: () -> Unit
//) : CustomDialogFragment {
//    val fragmentManager = parentFragmentManager
//    val dialogFragment = CustomDialogFragment.newInstance(image, title, description, btnText, onBtnClick)
//
//    val args = Bundle()
//    with(args) {
//        args.putInt(KEY_IMAGE_RES, image)
//        args.putString(KEY_TITLE, title)
//        args.putString(KEY_DESC, description)
//        args.putString(KEY_TEXT, btnText)
//        args.putSerializable(KEY_ON_CLICK, onBtnClick as Serializable)
//        dialogFragment.arguments = this
//    }
//
//    dialogFragment.show(fragmentManager, CustomDialogFragment::class.java.simpleName)
//    return dialogFragment
//}

@Suppress("DEPRECATION")
fun Fragment.showCustomToast(textAlert: String?) {
    val inflater = layoutInflater
    val layout = inflater.inflate(
        R.layout.layout_toast,
        view?.findViewById(R.id.layout_custom_root)
    )
    val textView: TextView = layout.findViewById(R.id.txToastMessage)
    textView.text = textAlert
    with(Toast(context?.applicationContext)) {
        duration = Toast.LENGTH_SHORT
        setGravity(Gravity.CENTER or Gravity.BOTTOM, 0, 45)
        view = layout
        show()
    }
}