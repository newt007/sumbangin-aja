package com.bintangpoetra.sumbanginaja.presentation.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.DialogFragment
import com.bintangpoetra.sumbanginaja.databinding.DialogBarcodeBinding
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_FOOD_CODE
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_RAW_STRING
import com.bintangpoetra.sumbanginaja.utils.ext.showToast

class BarcodeDialogFragment : DialogFragment() {

    private var _binding: DialogBarcodeBinding? = null
    private val binding get() = _binding

    companion object {
        fun newInstance(): BarcodeDialogFragment {
            return BarcodeDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBarcodeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foodCode = requireArguments().getString(KEY_RAW_STRING, null)

        foodCode?.let {
            setupQR(it)
        }
    }

    private fun setupQR(foodCode: String) {
        val manager: WindowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4

        val qrgEncoder = QRGEncoder(
            foodCode, null,
            QRGContents.Type.TEXT,
            smallerDimension
        )
        qrgEncoder.colorBlack = Color.BLACK
        qrgEncoder.colorWhite = Color.WHITE
        try {
            val bitmap = qrgEncoder.bitmap
            binding?.imgQrcode?.setImageBitmap(bitmap)
        } catch (e: Exception) {
            showToast("Error occured")
        }
    }

}