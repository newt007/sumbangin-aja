package com.bintangpoetra.sumbanginaja.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentRegisterBinding
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class RegisterFragment: Fragment() {

    private var _fragmentRegisterBinding: FragmentRegisterBinding? = null
    private val binding get() = _fragmentRegisterBinding!!

    private val registerViewModel: RegisterViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return _fragmentRegisterBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
        initObservers()
    }

    private fun initUI() {
        binding.lottieRegister.initLottie()
    }

    private fun initAction() {
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

    private fun initObservers() {
        registerViewModel.registerUserResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
                is ApiResponse.Success -> {
                    showToast(response.data)
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