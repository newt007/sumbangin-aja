package com.bintangpoetra.sumbanginaja.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentProfileBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by inject()

    private val pref: PreferenceManager by lazy { getPrefManager() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
        binding.apply {
            tvName.text = pref.getName
            tvEmail.text = pref.getEmail
            lottieLogin.initLottie()
        }
    }

    override fun initAction() {
        binding.apply {
            btnAccount.click {
                findNavController().navigate(R.id.action_profileFragment_to_accountFragment)
            }
            btnLogout.click {
                showConfirmDialog(
                    onPositiveClick = {
                        viewModel.logout()
                    }
                )
            }
            btnQrCode.click {
                showBarcodeDialog(pref.getUserId.toString())
            }
            btnFoodList.click {
                findNavController().navigate(R.id.action_profileFragment_to_foodListFragment)
            }
            btnQrScanner.click {
                findNavController().navigate(R.id.action_profileFragment_to_scannerFragment)
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.logoutResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.viewBgDimmer)
                    }
                }
                is ApiResponse.Success -> {
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
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