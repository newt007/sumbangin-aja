package com.bintangpoetra.sumbanginaja.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentLoginBinding
import com.bintangpoetra.sumbanginaja.utils.ext.showToast
import org.koin.android.ext.android.inject

class LoginFragment: Fragment() {

    private var _fragmentLoginBinding: FragmentLoginBinding? = null
    private val binding get() = _fragmentLoginBinding!!

    private val loginViewModel: LoginViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return _fragmentLoginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginUser("bintang@gmail.com", "123456")
        initObservers()
    }

    private fun initObservers() {
        loginViewModel.loginUserResult.observe(viewLifecycleOwner) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                    showToast("Loading....")
                }
                is ApiResponse.Success -> {
                    showToast("Success....")
                }
                is ApiResponse.Error -> {
                    showToast("Error.....")
                }
                else -> {
                    showToast("Undefined.....")
                }
            }
        }
    }

}