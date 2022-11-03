package com.bintangpoetra.sumbanginaja.presentation.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentLoginBinding
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.lottieLogin.initLottie()
    }

    override fun initAction() {
        binding.apply {
            btnLogin.click {
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()

                when {
                    email.isEmpty() -> {
                        binding.edtEmail.showError(getString(R.string.error_email_must_not_empty))
                    }
                    password.isEmpty() -> {
                        binding.edtPassword.showError(getString(R.string.error_must_not_empty))
                    }
                    else -> {
                        loginViewModel.loginUser(email, password)
                    }
                }
            }
            btnHaventAccount.click {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        loginViewModel.loginUserResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
                is ApiResponse.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is ApiResponse.Error -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.viewBgDimmer)

                        showOkDialog(getString(R.string.error_login_failed))
                    }
                }
                else -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
            }
        }
    }

}