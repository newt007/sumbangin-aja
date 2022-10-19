package com.bintangpoetra.sumbanginaja.presentation.profile.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentAccountBinding
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.hideLoading
import com.bintangpoetra.sumbanginaja.utils.ext.initLottie
import com.bintangpoetra.sumbanginaja.utils.ext.showLoading
import org.koin.android.ext.android.inject

class AccountFragment: Fragment() {

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
        initProcess()
        initObservers()
    }

    private fun initUI() {
        binding.lottieAccount.initLottie()
    }

    private fun initProcess() {
        pref.getToken?.let { token ->
            accountViewModel.getProfileDetail(token)
        }
    }

    private fun initObservers() {
        accountViewModel.profileDetailResult.observe(viewLifecycleOwner) { response ->
            when(response) {
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
    }

}