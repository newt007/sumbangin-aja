package com.bintangpoetra.sumbanginaja.presentation.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentFoodDetailBinding
import com.bintangpoetra.sumbanginaja.domain.food.model.Food
import com.bintangpoetra.sumbanginaja.utils.PreferenceManager
import com.bintangpoetra.sumbanginaja.utils.ext.*
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.koin.android.ext.android.inject
import timber.log.Timber

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private val foodDetailViewModel: FoodDetailViewModel by inject()

    private lateinit var mMap: GoogleMap

    private val pref: PreferenceManager by lazy { getPrefManager() }

    private var isOwnedFood = false

    private var mFood: Food? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs = arguments?.let { FoodDetailFragmentArgs.fromBundle(it) }
        val id = safeArgs?.id ?: 0

        foodDetailViewModel.getFoodDetail(id)

        initUI()
        initAction()
        initObservers()
    }

    private fun initUI(){
        binding.lottieLoading.initLottie()
    }

    private fun initAction() {
        binding.apply {
            btnDetailAction.click {
                if (isOwnedFood) {
                    showBarcodeDialog(mFood?.foodGenerateCode.toString())
                } else {
                    showToast("Food Ranger gasno")
                }
            }
        }
    }

    private fun initObservers() {
        foodDetailViewModel.foodDetailResult.observe(viewLifecycleOwner) { response ->
            Timber.d("Response is $response")
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(binding.loadingContainer)
                }
                is ApiResponse.Success -> {
                    val food = response.data
                    mFood = food
                    binding.apply {
                        imvFood.setImageUrl(food.images)
                        imvFoodOwner.setImageUrl(food.user?.profileUsers.toString())

                        tvFoodName.text = food.name
                        tvFoodOwner.text = food.user?.name
                        tvFoodDescription.text = food.descriptions
                        tvAddress.text = food.user?.address

                        if (food.user?.id.toString().isTheFoodOwner(pref.getUserId.toString())) {
                            isOwnedFood = true
                            btnDetailAction.text = getString(R.string.label_give_food)
                        } else {
                            isOwnedFood = false
                            btnDetailAction.text = getString(R.string.label_call_food_ranger)
                        }

                        val mapFragment =
                            childFragmentManager.findFragmentById(R.id.containerMap) as SupportMapFragment?

                        mapFragment?.getMapAsync { googleMap ->
                            mMap = googleMap
                            val foodLocation = LatLng(food.latitude, food.longitude)
                            mMap.addSingleMarker(
                                location = foodLocation,
                                markerName = food.name,
                                tag = food.id.toString()
                            )
                            mMap.animateCameraToSingleMarker(foodLocation)
                        }
                    }
                    hideLoading(binding.loadingContainer)
                }
                is ApiResponse.Error -> {
                    hideLoading(binding.loadingContainer)

                }
                else -> {
                    hideLoading(binding.loadingContainer)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}