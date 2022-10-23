package com.bintangpoetra.sumbanginaja.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentProfileBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private var _fragmentProfileBinding: FragmentProfileBinding? = null
    private val binding get() = _fragmentProfileBinding!!

    private val pref: PreferenceManager by lazy { getPrefManager() }

    private val viewModel: ProfileViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return _fragmentProfileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
        initObservers()
    }

    private fun initUI() {
        binding.apply {
            tvName.text = pref.getName
            tvEmail.text = pref.getEmail
            lottieLogin.initLottie()
        }
    }

    private fun initAction() {
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

    private fun initObservers() {
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