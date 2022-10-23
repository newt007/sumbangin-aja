package com.bintangpoetra.sumbanginaja.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.databinding.FragmentSplashBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.getPrefManager

class SplashFragment: Fragment() {

    private var _fragmentSplashBinding: FragmentSplashBinding? = null

    private val pref: PreferenceManager by lazy { getPrefManager() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return _fragmentSplashBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLoading()
    }

    private fun initLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (pref.isLogin) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }, 1500)
    }

}