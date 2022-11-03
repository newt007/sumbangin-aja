package com.bintangpoetra.sumbanginaja.presentation.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.base.ui.BaseFragment
import com.bintangpoetra.sumbanginaja.databinding.FragmentMapBinding
import com.bintangpoetra.sumbanginaja.utils.ext.addSingleMarker
import com.bintangpoetra.sumbanginaja.utils.ext.animateCameraToSingleMarker
import com.bintangpoetra.sumbanginaja.utils.ext.popClick
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapFragment : BaseFragment<FragmentMapBinding>() {

    private var markerName = ""
    private var mapTag = "0"
    private var location: LatLng? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)

    override fun initIntent() {
        val safeArgs = arguments?.let { MapFragmentArgs.fromBundle(it) }
        markerName = safeArgs?.name ?: ""
        mapTag = safeArgs?.tag ?: "0"
        val latitude = safeArgs?.latitude ?: 0.0
        val longitude = safeArgs?.longitude ?: 0.0
        location = LatLng(latitude.toDouble(), longitude.toDouble())
    }

    override fun initAction() {
        binding.btnBounds.popClick {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${location?.latitude},${location?.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    override fun initProcess() {}

    override fun initObservers() {}

    override fun initUI() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.containerMap) as SupportMapFragment?

        mapFragment?.getMapAsync { googleMap ->
            location?.let {
                googleMap.addSingleMarker(it, markerName, mapTag)
                googleMap.animateCameraToSingleMarker(it)
            }
        }
    }


}