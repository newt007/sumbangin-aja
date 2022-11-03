package com.bintangpoetra.sumbanginaja.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentOnboardingBinding

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
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }
}