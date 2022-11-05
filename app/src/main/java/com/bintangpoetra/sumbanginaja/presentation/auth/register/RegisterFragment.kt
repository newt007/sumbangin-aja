package com.bintangpoetra.sumbanginaja.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentRegisterBinding
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class RegisterFragment: BaseFragment<FragmentRegisterBinding>() {

    private val registerViewModel: RegisterViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initProcess() {
    }

    override fun initUI() {
        binding.lottieRegister.initLottie()
    }

    override fun initAction() {
        binding.apply {
            btnRegister.click {
                val fullName = binding.edtFullName.text.toString()
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()

                when {
                    fullName.isEmpty() -> {
                        binding.edtFullName.showError(getString(R.string.error_name_must_not_empty))
                    }
                    email.isEmpty() -> {
                        binding.edtEmail.showError(getString(R.string.error_email_must_not_empty))
                    }
                    password.isEmpty() -> {
                        binding.edtPassword.showError(getString(R.string.error_must_not_empty))
                    }
                    password.length < 8 -> {
                        binding.edtPassword.showError(getString(R.string.message_password_must_8_character))
                    }
                    else -> {
                        registerViewModel.registerUser(fullName, email, password, "default")
                    }
                }
            }
            btnHaventAccount.click {
                findNavController().popBackStack()
            }
        }
    }

    override fun initObservers() {
        registerViewModel.registerUserResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
                is ApiResponse.Success -> {
                    showCustomToast(response.data)
                    requireView().findNavController().popBackStack()

                }
                is ApiResponse.Error -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.viewBgDimmer)
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