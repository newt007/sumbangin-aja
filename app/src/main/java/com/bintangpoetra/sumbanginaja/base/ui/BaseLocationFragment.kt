package com.bintangpoetra.sumbanginaja.base.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.presentation.dialog.CustomDialogFragment
import com.bintangpoetra.sumbanginaja.utils.BundleKeys
import com.bintangpoetra.sumbanginaja.utils.ConstVal
import com.bintangpoetra.sumbanginaja.utils.ext.showCustomToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import timber.log.Timber
import java.io.Serializable

@Suppress("DEPRECATION")
abstract class BaseLocationFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    private var customDialog: CustomDialogFragment? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    private val priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    private val cancellationTokenSource = CancellationTokenSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, savedInstanceState)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIntent()
        initUI()
        initServicesCheckAndCallApi()
        initAction()
        initProcess()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB

    abstract fun initIntent()

    abstract fun initUI()

    abstract fun initAction()

    abstract fun initProcess()

    abstract fun initObservers()

    private fun initServicesCheckAndCallApi() {
        Timber.d("Calleddd init services")
        if (!isLocationEnabled()) {
            showPermissionDialog()
        } else {
            initPermissionAndGetLocation()
            initObservers()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun showPermissionDialog() {
        showCustomDialog(
            image = R.drawable.ic_enable_location,
            title = getString(R.string.title_ask_permission_location),
            description = getString(R.string.label_location_access),
            btnText = getString(R.string.action_allowed),
            onBtnClick = {
                dismissCustomDialog()
                initServicesCheckAndCallApi()
            }
        )
    }

    protected fun getLocation(onLocationReceived: (location: Location) -> Unit) {
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

        mFusedLocationClient?.getCurrentLocation(priority, cancellationTokenSource.token)
            ?.addOnSuccessListener { location ->
                onLocationReceived(location)
            }
            ?.addOnFailureListener { exception ->
                showCustomToast(exception.message)
            }
    }

    protected fun initPermissionAndGetLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        permReqLauncher.launch(ConstVal.LOCATION_PERMISSION)
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                getLocation {
                    Timber.d("lat ${it.latitude}, lng ${it.longitude}")
                }
            }
        }

    private fun showCustomDialog(
        @DrawableRes image: Int,
        title: String,
        description: String,
        btnText: String,
        onBtnClick: () -> Unit
    ) {
        val fragmentManager = parentFragmentManager
        customDialog =
            CustomDialogFragment.newInstance(image, title, description, btnText, onBtnClick)

        val args = Bundle()
        with(args) {
            args.putInt(BundleKeys.KEY_IMAGE_RES, image)
            args.putString(BundleKeys.KEY_TITLE, title)
            args.putString(BundleKeys.KEY_DESC, description)
            args.putString(BundleKeys.KEY_TEXT, btnText)
            args.putSerializable(BundleKeys.KEY_ON_CLICK, onBtnClick as Serializable)
            customDialog?.arguments = this
        }

        customDialog?.show(fragmentManager, CustomDialogFragment::class.java.simpleName)
    }

    private fun dismissCustomDialog() {
        customDialog?.dismiss()
    }

}