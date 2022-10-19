package com.bintangpoetra.sumbanginaja.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentHomeBinding
import com.bintangpoetra.sumbanginaja.presentation.auth.login.LoginViewModel
import com.bintangpoetra.sumbanginaja.utils.ConstVal
import com.bintangpoetra.sumbanginaja.utils.ext.*
import org.koin.android.ext.android.inject

class HomeFragment: Fragment() {
    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = _fragmentHomeBinding!!

    private val homeViewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().showExitaAppDialog()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return _fragmentHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActions()
        initObservers()
    }

    private fun initActions() {
        binding.apply {
            btnFoodType.popClick {
                updateTabState(FOOD_TYPE)
            }

            btnNonFoodType.popClick {
                updateTabState(NON_FOOD_TYPE)
            }
        }
    }

    private fun initObservers() {
        homeViewModel.foodResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showShimmerLoading(it.homeShimmeringLoading, it.llShimmeringContainer)
                    }
                }
                is ApiResponse.Success -> {
                    binding.let {
                        hideShimmerLoading(it.homeShimmeringLoading, it.llShimmeringContainer)

                    }
                }
                is ApiResponse.Error -> {
                    binding.let {
                        hideShimmerLoading(it.homeShimmeringLoading, it.llShimmeringContainer)
                    }
                }
                else -> {
                    binding.let {
                        hideShimmerLoading(it.homeShimmeringLoading, it.llShimmeringContainer)
                    }
                }
            }
        }
    }

    private fun updateTabState(selectedType: String) {
        resetTabState()
        if (selectedType == FOOD_TYPE) {
            binding.apply {
                btnFoodType.setBackgroundResource(R.drawable.bg_chip_selected)
                btnFoodType.setTextColor(resources.getColor(R.color.colorPrimaryGreen, resources.newTheme()))
            }
        }

        if (selectedType == NON_FOOD_TYPE){
            binding.apply {
                btnNonFoodType.setBackgroundResource(R.drawable.bg_chip_selected)
                btnNonFoodType.setTextColor(resources.getColor(R.color.colorPrimaryGreen, resources.newTheme()))
            }
        }
    }

    private fun resetTabState(){
        binding.apply {
            btnFoodType.setBackgroundResource(android.R.color.transparent)
            btnFoodType.setTextColor(resources.getColor(R.color.white, resources.newTheme()))

            btnNonFoodType.setBackgroundResource(android.R.color.transparent)
            btnNonFoodType.setTextColor(resources.getColor(R.color.white, resources.newTheme()))
        }
    }

    companion object {
        const val FOOD_TYPE = "food type"
        const val NON_FOOD_TYPE = "non food type"
    }
}
