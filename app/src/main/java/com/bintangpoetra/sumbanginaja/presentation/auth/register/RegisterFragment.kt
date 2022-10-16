package com.bintangpoetra.sumbanginaja.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {

    private var _fragmentRegisterBinding: FragmentRegisterBinding? = null
    private val binding get() = _fragmentRegisterBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return _fragmentRegisterBinding?.root
    }

}