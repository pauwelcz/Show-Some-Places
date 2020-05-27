package com.example.showsomeplaces.ui.location

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.*

class LocationManager(private var locationClient: FusedLocationProviderClient?)
{
    private val locationRequest = LocationRequest().apply {
        interval = 3000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    val locationSettingsRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .build()!!

    private var currentLocation: Location? = null
    private val listeners: MutableList<(Location) -> Unit> = mutableListOf()

    private val locationCallback = object : LocationCallback()
    {
        override fun onLocationResult(locationResult: LocationResult?)
        {
            locationResult?.let {
                currentLocation = it.lastLocation

                for (listener in listeners)
                    listener(it.lastLocation)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun start()
    {
        locationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun stop()
    {
        locationClient?.removeLocationUpdates(locationCallback)
    }

    fun destroy()
    {
        locationClient = null
        listeners.clear()
    }

    fun addListener(listener: (Location) -> Unit)
    {
        listeners.add(listener)
    }

    fun removeListener(listener: (Location) -> Unit)
    {
        listeners.remove(listener)
    }

    fun getCurrentLocation() = currentLocation
}