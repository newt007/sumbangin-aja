package com.bintangpoetra.sumbanginaja.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentOnboardingBinding
import com.bintangpoetra.sumbanginaja.utils.ext.click
import com.bintangpoetra.sumbanginaja.utils.ext.showCustomDialog

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): FragmentOnboardingBinding =
        FragmentOnboardingBinding.inflate(layoutInflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
        binding.btnStart.click {
            showCustomDialog(
                image = R.drawable.ic_enable_location,
                title = getString(R.string.title_ask_permission_location),
                description = getString(R.string.label_location_access),
                btnText = getString(R.string.action_allowed),
                onBtnClick = {
                    findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
                }
            )
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

}