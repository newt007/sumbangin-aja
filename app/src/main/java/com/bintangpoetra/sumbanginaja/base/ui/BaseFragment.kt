package com.bintangpoetra.sumbanginaja.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bintangpoetra.sumbanginaja.presentation.dialog.CustomDialogFragment
import com.bintangpoetra.sumbanginaja.utils.BundleKeys
import java.io.Serializable

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    var customDialog: CustomDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, savedInstanceState)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIntent()
        initUI()
        initAction()
        initProcess()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): VB

    abstract fun initIntent()

    abstract fun initUI()

    abstract fun initAction()

    abstract fun initProcess()

    abstract fun initObservers()

    protected fun showCustomDialog(
        @DrawableRes image: Int,
        title: String,
        description: String,
        btnText: String,
        onBtnClick: () -> Unit
    ) {
        val fragmentManager = parentFragmentManager
        customDialog = CustomDialogFragment.newInstance(image, title, description, btnText, onBtnClick)

        val args = Bundle()
        with(args) {
            args.putInt(BundleKeys.KEY_IMAGE_RES, image)
            args.putString(BundleKeys.KEY_TITLE, title)
            args.putString(BundleKeys.KEY_DESC, description)
            args.putString(BundleKeys.KEY_TEXT, btnText)
            args.putSerializable(BundleKeys.KEY_ON_CLICK, onBtnClick as Serializable)
            customDialog?.arguments = this
        }

        customDialog?.show(fragmentManager, CustomDialogFragment::class.java.simpleName)
    }

    protected fun dismissCustomDialog(){
        customDialog?.dismiss()
    }

}