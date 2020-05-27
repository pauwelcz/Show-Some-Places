package com.example.showsomeplaces.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.R
import com.example.showsomeplaces.model.REQUEST_ACCESS_FINE_LOCATION
import com.example.showsomeplaces.repository.PlaceRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private val placeRepository: PlaceRepository? by lazy { context?.let { PlaceRepository(it) } }

    companion object {
        lateinit var mapFragment: SupportMapFragment
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val REQUEST_PERMISSIONS = 100


    private var myLongitude: Double? = null
    private var myLatitude: Double? = null



    // private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) }
            == PackageManager.PERMISSION_GRANTED
        ) {
            println("location granted")
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_ACCESS_FINE_LOCATION
            )
        }

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)

        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        //

        this@HomeFragment.googleMap = googleMap!!
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            // googleMap.setMyLocationEnabled(true);
        googleMap.clear() //clear old markers
        // googleMap.setMyLocationEnabled(true)
        seedMarkersFromDB()
        // Add a marker in Sydney and move the camera

        /* val yourCurrentLocation = LatLng(49.0145783,17.2379817)
        googleMap.addMarker(
            MarkerOptions()
                .position(yourCurrentLocation)
                .title("Your current location")
                .icon(
                    BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                )
        ).showInfoWindow() */
        /*
            Nastavim, abych na to vubec klikal
         */
        googleMap.setOnMarkerClickListener(this)

        if (activity?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED && getActivity()?.let {
                ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.ACCESS_COARSE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.isMyLocationEnabled = true

    }

    /*
        adding markers from database
     */
    private fun seedMarkersFromDB(): Boolean {
        // adding my places to markers
        val placesToMark = placeRepository?.getAllPlaces() ?: listOf()
        for (i in placesToMark.indices) {
            val latitude = placesToMark[i].latitude
            val longitude = placesToMark[i].longitude
            val title = placesToMark[i].title
            val note = placesToMark[i].note
            val poi = placesToMark[i].poi
            // mam to v try pro sichr kvuli tomu, ze jsem tam na zacatku vkladal spatne data, alespon, pokud to nezacnu mazat
            try {
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(latitude.toDouble(), longitude.toDouble()))
                        .title(title)
                        .snippet(poi)
                )
            }  catch (e: NumberFormatException) { null }


        }
        return true
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p0?.position, 15.0f))
        p0?.showInfoWindow()
        Toast.makeText(context, "For whole map just click again on Home icon.", Toast.LENGTH_LONG).show()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_ACCESS_FINE_LOCATION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            println("ACCESS fine location")
        }
    }
}
