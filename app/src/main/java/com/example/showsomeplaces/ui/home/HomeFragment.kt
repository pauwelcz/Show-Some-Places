package com.example.showsomeplaces.ui.home

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.R
import com.example.showsomeplaces.repository.PlaceRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : Fragment() {

    private val placeRepository: PlaceRepository? by lazy { context?.let { PlaceRepository(it) } }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    private lateinit var lastLocation: Location
    private lateinit var mMap: GoogleMap

    fun MapFragment() {
        // Required empty public constructor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment? //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment

        mapFragment!!.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.clear() //clear old markers

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
            val zeravice = LatLng(49.0145783,17.2379817)
            mMap.addMarker(
                MarkerOptions().position(LatLng(49.0145783,17.2379817))
                    .title("Marker in Zeravice")
                    .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CameraUpdateFactory.newLatLng(zeravice), 14.0f))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zeravice, 15f))
            // CameraUpdateFactory.newLatLngZoom()


        }

        return view
    }
}
