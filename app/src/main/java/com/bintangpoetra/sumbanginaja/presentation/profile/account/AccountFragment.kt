package com.bintangpoetra.sumbanginaja.presentation.profile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentAccountBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class AccountFragment : Fragment() {

    private var _fragmentAccountBinding: FragmentAccountBinding? = null
    private val binding get() = _fragmentAccountBinding!!

    private val accountViewModel: AccountViewModel by inject()
    private lateinit var pref: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentAccountBinding = FragmentAccountBinding.inflate(inflater, container, false)
        return _fragmentAccountBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = PreferenceManager(requireContext())

        initUI()
        initAction()
        initProcess()
        initObservers()
    }

    private fun initUI() {
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

    private fun initAction() {
        binding.apply {
            btnSave.click {
                val fullName = edtFullName.text.toString()
                val address = edtAddress.text.toString()

                when {
                    fullName.isEmpty() -> {
                        edtFullName.showError(getString(R.string.error_name_must_not_empty))
                    }
                    address.isEmpty() -> {
                        edtAddress.showError(getString(R.string.error_address_must_not_empty))
                    }
                    else -> {
                        accountViewModel.updateProfile(fullName, address)
                    }
                }
            }
        }
    }

    private fun initProcess() {
        pref.getToken?.let { token ->
            accountViewModel.getProfileDetail()
        }
    }

    private fun initObservers() {
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
            when(response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
                is ApiResponse.Success -> {
                    binding.apply {
                        hideLoading(viewBgWhite, viewBgDimmer)
                        showToast(getString(R.string.message_update_profile_success))
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