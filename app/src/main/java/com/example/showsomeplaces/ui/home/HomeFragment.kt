package com.example.showsomeplaces.ui.home

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.MainActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.repository.PlaceRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var currentPositionMarker: MarkerOptions? = null
    private var currentLocationMarker: Marker? = null

    private val placeRepository: PlaceRepository? by lazy { context?.let { PlaceRepository(it) } }

    companion object {
        lateinit var mapFragment: SupportMapFragment
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private lateinit var googleMap: GoogleMap
    private val REQUEST_PERMISSIONS = 100


    private var currentLatitude: String? = null
    private var currentLongitude: String? = null
    private var latitudeToAnimate: String? = null
    private var longitudeToAnimate: String? = null



    // private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //currentLatitude = (activity as MainActivity).currentLatitude
        //currentLongitude = (activity as MainActivity).currentLongitude
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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


        // val currentLatitude = (activity as MainActivity).currentLatitude
        // val currentLongitude = (activity as MainActivity).currentLongitude
        if (currentLatitude != "100000.0" && currentLongitude != "100000.0") {
            val yourCurrentLocation = currentLatitude?.toDouble()?.let {
                currentLongitude?.toDouble()?.let { it1 ->
                    LatLng(
                        it,
                        it1
                    )
                }
            }
            googleMap.addMarker(
                yourCurrentLocation?.let {
                    MarkerOptions()
                        .position(it)
                        .title("Your current location")
                        .icon(
                            BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                        )
                }
            ).showInfoWindow()
        }
        /*
            Nastavim, abych na to vubec klikal
         */
        if (latitudeToAnimate !== null && longitudeToAnimate !== null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitudeToAnimate!!.toDouble(), longitudeToAnimate!!.toDouble()), 15.0f))
        }
        googleMap.setOnMarkerClickListener(this)
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
            val poi = placesToMark[i].poi
            // mam to v try pro sichr kvuli tomu, ze jsem tam na zacatku vkladal spatne data, alespon, pokud to nezacnu mazat
            try {
                googleMap.addMarker(
                    MarkerOptions().position(LatLng(latitude.toDouble(), longitude.toDouble()))
                        .title(title)
                        .snippet(poi)
                )
            }  catch (e: NumberFormatException) {
                println(e)
            }


        }
        return true
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p0?.position, 15.0f))
        p0?.showInfoWindow()
        Toast.makeText(context, "For whole map just click again on Home icon.", Toast.LENGTH_LONG).show()
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        currentLatitude = (activity as MainActivity).currentLatitude
        currentLongitude = (activity as MainActivity).currentLongitude
        latitudeToAnimate = (activity as MainActivity).myLatitude
        longitudeToAnimate = (activity as MainActivity).myLongitude
    }

}
