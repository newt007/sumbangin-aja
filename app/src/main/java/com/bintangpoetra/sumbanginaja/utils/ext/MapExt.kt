package com.bintangpoetra.sumbanginaja.utils.ext

import com.bintangpoetra.sumbanginaja.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun GoogleMap.addSingleMarker(
    location: LatLng,
    markerName: String,
    tag: String,
) {
    this.addMarker(createMarkerOptions(location, markerName))?.tag = tag
}

fun GoogleMap.animateCameraToSingleMarker(location: LatLng) {
    val zoomLevel = 17.5f
    val cu = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
    this.animateCamera(cu, 1000, null)
}


private fun createMarkerOptions(
    location: LatLng,
    markerName: String,
): MarkerOptions {
    val iconDrawable = R.drawable.food_location_marker
    val icon = BitmapDescriptorFactory.fromResource(iconDrawable)
    return MarkerOptions().position(location)
        .title(markerName)
        .icon(icon)
}