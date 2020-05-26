package com.example.showsomeplaces.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.R
import com.example.showsomeplaces.repository.PlaceRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val placeRepository: PlaceRepository? by lazy { context?.let { PlaceRepository(it) } }

    companion object {
        var mapFragment: SupportMapFragment?=null
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var mMap: GoogleMap

    fun MapFragment() {
        // Required empty public constructor
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)


        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        //
        mMap = googleMap!!
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.clear() //clear old markers
        seedMarkersFromDB()
        // Add a marker in Sydney and move the camera

        val yourCurrentLocation = LatLng(49.0145783,17.2379817)
        mMap.addMarker(MarkerOptions()
            .position(yourCurrentLocation)
            .title("Your current location")
            .icon(
                BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yourCurrentLocation))

        mMap.setOnMarkerClickListener(this);
    }

    /*
        adding markers
     */
    private fun seedMarkersFromDB(): Boolean {
        // adding my places to markers
        val placesToMark = placeRepository?.getAllPlaces() ?: listOf()
        for (i in placesToMark.indices) {
            val latitude = placesToMark[i].latitude
            val longitude = placesToMark[i].longitude
            val title = placesToMark[i].title
            val note = placesToMark[i].note
            // mam to v try pro sichr kvuli tomu, ze jsem tam na zacatku vkladal spatne data, alespon, pokud to nezacnu mazat
            try {
                mMap.addMarker(
                    MarkerOptions().position(LatLng(latitude.toDouble(), longitude.toDouble()))
                        .title(title)
                        .snippet(note)
                )
            }  catch (e: NumberFormatException) { null }


        }
        return true
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p0?.position, 15.0f))
        p0?.showInfoWindow()
        Toast.makeText(context, "MARKER CLICKED", Toast.LENGTH_LONG).show()
        return true
    }

}
