package com.bintangpoetra.sumbanginaja.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentHomeBinding
import com.bintangpoetra.sumbanginaja.presentation.food.list.FoodListFragmentDirections
import com.bintangpoetra.sumbanginaja.presentation.home.adapter.FoodAdapter
import com.bintangpoetra.sumbanginaja.utils.ConstVal.LOCATION_PERMISSION
import com.bintangpoetra.sumbanginaja.utils.ext.hideShimmerLoading
import com.bintangpoetra.sumbanginaja.utils.ext.popClick
import com.bintangpoetra.sumbanginaja.utils.ext.showExitaAppDialog
import com.bintangpoetra.sumbanginaja.utils.ext.showShimmerLoading
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject
import timber.log.Timber


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by inject()

    private val adapter: FoodAdapter by lazy { FoodAdapter { toDetail(it) } }
    private var myLocation: Location? = null
    var mFusedLocationClient: FusedLocationProviderClient? = null

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
        initPermission()

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

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(requireContext())
                .setTitle("Location Permission Needed")
                .setMessage("This app needs the Location permission, please accept to use location functionality")
                .setPositiveButton(
                    "OK"
                ) { p0, _ ->
                    permReqLauncher.launch(LOCATION_PERMISSION)
                    p0.dismiss()
                }
                .create()
                .show()
        }

        mFusedLocationClient?.lastLocation?.addOnCompleteListener {
            myLocation = it.result
            Timber.d("location latitude ${it.result.latitude}")
            adapter.setMyLocation(it.result)
        }
    }


    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                getLocation()
            }
        }

    private fun toDetail(foodId: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToFoodDetailFragment(
                foodId
            )
        )
    }

    private fun initPermission() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        permReqLauncher.launch(LOCATION_PERMISSION)
    }

    companion object {
        const val FOOD_TYPE = "food type"
        const val NON_FOOD_TYPE = "non food type"
    }
}
