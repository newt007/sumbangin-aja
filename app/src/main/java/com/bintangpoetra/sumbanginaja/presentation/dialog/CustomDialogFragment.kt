package com.bintangpoetra.sumbanginaja.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.DialogFragment
import com.bintangpoetra.sumbanginaja.databinding.LayoutCustomDialogBinding
import com.bintangpoetra.sumbanginaja.utils.BundleKeys.KEY_ON_CLICK
import com.bintangpoetra.sumbanginaja.utils.ext.click

class CustomDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(
            @DrawableRes image: Int?,
            title: String,
            description: String,
            btnText: String,
            onBtnClick: () -> Unit
        ): CustomDialogFragment = CustomDialogFragment().apply {
            arguments = Bundle().apply { }
            this.illustrationImage = image
            this.title = title
            this.description = description
            this.btnText = btnText
            this.onBtnClick = onBtnClick
        }
    }

    @DrawableRes
    private var illustrationImage: Int? = null
    private var title: String? = null
    private var description: String? = null
    private var btnText: String? = null

    private var onBtnClick: (() -> Unit)? = null

    private var _binding: LayoutCustomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutCustomDialogBinding.inflate(inflater, container, false)

        dialog?.apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
    }

    private fun initUI() {
        binding.apply {
            illustrationImage?.let {
                imgDrawable.setImageResource(it)
            }
            tvTitle.text = title.toString()
            tvDescription.text = description.toString()
            btnDialog.text = btnText
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun initAction() {
        binding.apply {
            val onClickCallBack: () -> Unit = arguments?.getSerializable(KEY_ON_CLICK) as () -> Unit
            btnDialog.click {
                onClickCallBack.invoke()
                dismiss()
            }
        }
    }

}