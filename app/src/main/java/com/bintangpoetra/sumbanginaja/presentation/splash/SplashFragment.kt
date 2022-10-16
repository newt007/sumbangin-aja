package com.bintangpoetra.sumbanginaja.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {

    private var _fragmentSplashBinding: FragmentSplashBinding? = null
    private val binding get() = _fragmentSplashBinding!!

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
    }

}