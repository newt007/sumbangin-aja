package com.bintangpoetra.sumbanginaja.presentation.food.add

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.data.lib.ApiResponse
import com.bintangpoetra.sumbanginaja.databinding.FragmentAddFoodBinding
import com.bintangpoetra.sumbanginaja.domain.region.model.Region
import com.bintangpoetra.sumbanginaja.presentation.home.HomeFragmentDirections
import com.bintangpoetra.sumbanginaja.presentation.region.city.CityFragment
import com.bintangpoetra.sumbanginaja.presentation.region.province.ProvinceFragment
import com.bintangpoetra.sumbanginaja.utils.ConstVal
import com.bintangpoetra.sumbanginaja.utils.ext.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.io.File

class AddFoodFragment : Fragment() {

    private var _binding: FragmentAddFoodBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddFoodViewModel by inject()
    var mFusedLocationClient: FusedLocationProviderClient? = null

    private var imageFile: File? = null
    private var provinceId = 0
    private var provinceName = ""
    private var cityId = 0
    private var cityName = ""
    private var myLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        initUI()
        initActions()
        initObservers()
        initResultData()
    }

    private fun initUI(){
        binding.lottieLoading.initLottie()
        binding.toolbarAccount.apply {
            title = context.getString(R.string.title_add_food)
            setNavigationOnClickListener {
                it.findNavController().popBackStack()
            }
        }
    }

    private fun initResultData(){
        setFragmentResultListener(ProvinceFragment.PROVINCE_ID_KEY) { _, bundle ->
            provinceId = bundle.get(ProvinceFragment.PROVINCE_ID_BUNDLE) as Int
            refreshProvinceAndCity()
        }

        setFragmentResultListener(ProvinceFragment.PROVINCE_NAME_KEY) { _, bundle ->
            provinceName = bundle.get(ProvinceFragment.PROVINCE_NAME_BUNDLE) as String
            refreshProvinceAndCity()
        }

        setFragmentResultListener(CityFragment.CITY_ID_KEY) { _, bundle ->
            cityId = bundle.get(CityFragment.CITY_ID_BUNDLE) as Int
        }

        setFragmentResultListener(CityFragment.CITY_NAME_KEY) { _, bundle ->
            cityName = bundle.get(CityFragment.CITY_NAME_BUNDLE) as String
            refreshProvinceAndCity()
        }
    }

    private fun refreshProvinceAndCity(){
        if (provinceName.isNotEmpty()){
            binding.tvMenuTitleProvince.text = provinceName
        }

        if (cityName.isNotEmpty()) {
            binding.tvMenuTitleCity.text = cityName
        }
    }

    private fun initActions(){

        binding.btnAddFood.click {
            submitCreateFood()
        }

        binding.btnProvince.popClick {
            findNavController().navigate(R.id.action_addFoodFragment_to_provinceFragment)
        }

        binding.btnCity.popClick {
            if (provinceId == 0) {
                binding.root.showSnackBar("Pilih provinsi terlebih dahulu..")
                return@popClick
            }
            val action = AddFoodFragmentDirections.actionAddFoodFragmentToCityFragment(provinceId)
            findNavController().navigate(action)
        }

        binding.imgFoodPlaceholder.popClick {
            initTakePicture()
        }

        binding.imgFood.popClick {
            initTakePicture()
        }

        binding.btnGetLocation.popClick {
            getLocation()
        }
    }

    private fun initTakePicture(){
        Dexter.withContext(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    showImageMenu()
                }
                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).withErrorListener {
                binding.root.showSnackBar("Aplikasi membutuhkan permission")
            }.onSameThread()
            .check()
    }

    private fun submitCreateFood(){
        binding.apply {
            val foodName = edtFoodName.text.toString()
            val foodDescription = edtDescription.text.toString()
            val address = edtAddress.text.toString()

            binding.apply {
                when {
                    foodName.isEmpty() -> {
                        edtFoodName.showError(getString(R.string.error_name_must_not_empty))
                    }
                    foodDescription.isEmpty() -> {
                        edtDescription.showError(getString(R.string.error_email_must_not_empty))
                    }
                    address.isEmpty() -> {
                        edtAddress.showError(getString(R.string.error_must_not_empty))
                    }
                    imageFile == null -> {
                        binding.root.showSnackBar("Foto masih kosong")
                    }
                    provinceId == 0 -> {
                        binding.root.showSnackBar("Belum milih provinsi")
                    }
                    cityId == 0 -> {
                        binding.root.showSnackBar("Belum memilih kota")
                    }
                    myLocation == null -> {
                        binding.root.showSnackBar("Belum mengambil posisi")
                    }
                    else -> {
                        viewModel.submitFood(
                            images = imageFile!!,
                            name = foodName,
                            description = foodDescription,
                            paybackTime = "1",
                            provinceId = provinceId,
                            cityId = cityId,
                            address = address,
                            location = myLocation
                        )
                    }
                }
            }
        }
    }

    private fun initObservers() {
        viewModel.addFoodResult.observe(viewLifecycleOwner) { response ->
            println("Response is $response")
            when (response) {
                is ApiResponse.Loading -> {
                    binding.let {
                        showLoading(it.viewBgWhite, it.backgroundDim)
                    }
                }
                is ApiResponse.Success -> {
                    showToast(response.data)
                    requireView().findNavController().popBackStack()
                }
                is ApiResponse.Error -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.backgroundDim)
                    }
                    showToast(response.errorMessage)
                }
                else -> {
                    binding.let {
                        hideLoading(it.viewBgWhite, it.backgroundDim)
                    }
                }
            }
        }
    }

    private fun showImageMenu() {
        AlertDialog.Builder(requireActivity())
            .setTitle("Pilih metode pengambilan Gambar")
            .setItems(R.array.pictures) { _, p1 ->
                if (p1 == 0) {
                    takePictureRegistration.launch()
                } else {
                    pickFileImage.launch("image/*")
                }
            }.create().show()
    }

    private val takePictureRegistration =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val uri = requireActivity().getImageUri(bitmap)
                imageFile = requireActivity().getFileFromUri(uri)
                binding.imgFoodPlaceholder.gone()
                binding.imgFood.show()
                binding.imgFood.setImageBitmap(bitmap)
            }
        }

    private val pickFileImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imageFile = requireActivity().getFileFromUri(uri)
                binding.imgFoodPlaceholder.gone()
                binding.imgFood.show()
                binding.imgFood.setImageURI(uri)
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
                    permReqLauncher.launch(ConstVal.LOCATION_PERMISSION)
                    p0.dismiss()
                }
                .create()
                .show()
        }

        mFusedLocationClient?.lastLocation?.addOnCompleteListener {
            myLocation = it.result
            binding.apply {
                tvLatitude.text = myLocation?.latitude.toString()
                tvLongitude.text = myLocation?.longitude.toString()
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}