package com.bintangpoetra.sumbanginaja.presentation.profile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentAccountBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private val accountViewModel: AccountViewModel by inject()
    private val pref: PreferenceManager by lazy { getPrefManager() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.apply {
            lottieAccount.initLottie()
            tilEmail.disable()
            toolbarAccount.apply {
                title = context.getString(R.string.title_account)
                setNavigationOnClickListener {
                    it.findNavController().popBackStack()
                }
            }
        }
    }

    override fun initAction() {
        binding.apply {
            btnSave.click {
                val fullName = edtFullName.text.toString()
                val address = edtAddress.text.toString()
                val phoneNumber = edtPhone.text.toString()

                when {
                    fullName.isEmpty() -> {
                        edtFullName.showError(getString(R.string.error_name_must_not_empty))
                    }
                    address.isEmpty() -> {
                        edtAddress.showError(getString(R.string.error_address_must_not_empty))
                    }
                    phoneNumber.isEmpty() -> {
                        edtPhone.showError(getString(R.string.message_phone_must_not_empty))
                    }
                    else -> {
                        accountViewModel.updateProfile(fullName, address, phoneNumber)
                    }
                }
            }
        }
    }

    override fun initProcess() {
        accountViewModel.getProfileDetail()
    }

    override fun initObservers() {
        accountViewModel.profileDetailResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
                is ApiResponse.Success -> {
                    binding.apply {
                        hideLoading(viewBgWhite, viewBgDimmer)

                        edtFullName.setText(response.data.name)
                        edtEmail.setText(response.data.email)
                        edtAddress.setText(response.data.address)
                        edtPhone.setText(response.data.phoneNumber)
                    }
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
        accountViewModel.updateProfileResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
                is ApiResponse.Success -> {
                    binding.apply {
                        hideLoading(viewBgWhite, viewBgDimmer)
                        showCustomToast(getString(R.string.message_update_profile_success))
                        findNavController().popBackStack()
                    }
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