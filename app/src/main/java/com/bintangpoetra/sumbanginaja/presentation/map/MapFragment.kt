package com.bintangpoetra.sumbanginaja.presentation.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bintangpoetra.sumbanginaja.R
import com.bintangpoetra.sumbanginaja.databinding.FragmentMapBinding
import com.bintangpoetra.sumbanginaja.utils.ext.addSingleMarker
import com.bintangpoetra.sumbanginaja.utils.ext.animateCameraToSingleMarker
import com.bintangpoetra.sumbanginaja.utils.ext.popClick
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var markerName = ""
    private var mapTag = "0"
    private var location: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initIntentData()
        initUI()
    }

    private fun initUI(){
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.containerMap) as SupportMapFragment?

        mapFragment?.getMapAsync { googleMap ->
            location?.let {
                googleMap.addSingleMarker(it, markerName, mapTag)
                googleMap.animateCameraToSingleMarker(it)
            }
        }

        binding.btnBounds.popClick {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${location?.latitude},${location?.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun initIntentData() {
        val safeArgs = arguments?.let { MapFragmentArgs.fromBundle(it) }
        markerName = safeArgs?.name ?: ""
        mapTag = safeArgs?.tag ?: "0"
        val latitude = safeArgs?.latitude ?: 0.0
        val longitude = safeArgs?.longitude ?: 0.0
        location = LatLng(latitude.toDouble(), longitude.toDouble())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}