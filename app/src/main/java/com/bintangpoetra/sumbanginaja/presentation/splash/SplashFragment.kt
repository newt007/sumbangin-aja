package com.bintangpoetra.sumbanginaja.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentSplashBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.getPrefManager

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val pref: PreferenceManager by lazy { getPrefManager() }

    private fun initLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (pref.isLogin) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
            }
        }, 1500)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        initLoading()
    }

    override fun initObservers() {
    }

}