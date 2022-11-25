package com.bintangpoetra.sumbanginaja.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseLocationFragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentHomeBinding
import com.bintangpoetra.sumbanginaja.presentation.home.adapter.FoodAdapter
import com.bintangpoetra.sumbanginaja.utils.ext.hideShimmerLoading
import com.bintangpoetra.sumbanginaja.utils.ext.popClick
import com.bintangpoetra.sumbanginaja.utils.ext.showExitaAppDialog
import com.bintangpoetra.sumbanginaja.utils.ext.showShimmerLoading
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeFragment : BaseLocationFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by inject()

    private val adapter: FoodAdapter by lazy { FoodAdapter { toDetail(it) } }

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

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initIntent() {
    }

    override fun initUI() {

        binding.rvHome.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHome.adapter = this.adapter
    }

    override fun initProcess() {
        homeViewModel.getFoods()
    }

    override fun initAction() {
        binding.apply {
            btnFoodType.popClick {
                updateTabState(FOOD_TYPE)
            }

            btnNonFoodType.popClick {
                updateTabState(NON_FOOD_TYPE)
            }
        }
    }

    override fun initObservers() {
        getLocation {
            adapter.setMyLocation(it)
        }

        homeViewModel.foodResult.observe(viewLifecycleOwner) { response ->
            Timber.d("Response is $response")
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showShimmerLoading(it.homeShimmeringLoading, it.llShimmeringContainer)
                    }
                }
                is ApiResponse.Success -> {
                    adapter.setData(response.data)
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
                btnFoodType.setTextColor(
                    resources.getColor(
                        R.color.colorPrimaryGreen,
                        resources.newTheme()
                    )
                )
            }
        }

        if (selectedType == NON_FOOD_TYPE) {
            binding.apply {
                btnNonFoodType.setBackgroundResource(R.drawable.bg_chip_selected)
                btnNonFoodType.setTextColor(
                    resources.getColor(
                        R.color.colorPrimaryGreen,
                        resources.newTheme()
                    )
                )
            }
        }
    }

    private fun resetTabState() {
        binding.apply {
            btnFoodType.setBackgroundResource(android.R.color.transparent)
            btnFoodType.setTextColor(resources.getColor(R.color.white, resources.newTheme()))

            btnNonFoodType.setBackgroundResource(android.R.color.transparent)
            btnNonFoodType.setTextColor(resources.getColor(R.color.white, resources.newTheme()))
        }
    }


    private fun toDetail(foodId: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToFoodDetailFragment(
                foodId
            )
        )
    }

    companion object {
        const val FOOD_TYPE = "food type"
        const val NON_FOOD_TYPE = "non food type"
    }
}
