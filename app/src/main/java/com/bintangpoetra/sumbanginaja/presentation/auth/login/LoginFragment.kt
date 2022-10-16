package com.bintangpoetra.sumbanginaja.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private var _fragmentLoginBinding: FragmentLoginBinding? = null
    private val binding get() = _fragmentLoginBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return _fragmentLoginBinding?.root
    }

}