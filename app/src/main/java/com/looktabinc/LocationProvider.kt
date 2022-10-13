package com.looktabinc

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager


class LocationProvider(context: Context, locationListener: LocationListener?) {
    /** location listener called on each location update  */
    private val locationListener: LocationListener?

    /** system's locationManager allowing access to GPS / Network position  */
    private val locationManager: LocationManager?

    /** is gpsProvider and networkProvider enabled in system settings  */
    private var gpsProviderEnabled: Boolean
    private var networkProviderEnabled: Boolean
    fun onPause() {
        if (locationListener != null && locationManager != null && (gpsProviderEnabled || networkProviderEnabled)) {
            locationManager.removeUpdates(locationListener)
        }
    }

    @SuppressLint("MissingPermission")
    fun onResume() {
        if (locationManager != null && locationListener != null) {

            // check which providers are available are available
            gpsProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            networkProviderEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            /** is GPS provider enabled?  */
            if (gpsProviderEnabled) {
                val lastKnownGPSLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (lastKnownGPSLocation != null && lastKnownGPSLocation.getTime() > System.currentTimeMillis() - LOCATION_OUTDATED_WHEN_OLDER_MS) {
                    locationListener.onLocationChanged(lastKnownGPSLocation)
                }
                if (locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME_GPS.toLong(),
                        LOCATION_UPDATE_DISTANCE_GPS.toFloat(),
                        locationListener
                    )
                }
            }
            /** is Network / WiFi positioning provider available?  */
            if (networkProviderEnabled) {
                val lastKnownNWLocation: Location? =
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (lastKnownNWLocation != null && lastKnownNWLocation.getTime() > System.currentTimeMillis() - LOCATION_OUTDATED_WHEN_OLDER_MS) {
                    locationListener.onLocationChanged(lastKnownNWLocation)
                }
                if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null) {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME_NW.toLong(),
                        LOCATION_UPDATE_DISTANCE_NW.toFloat(),
                        locationListener
                    )
                }
            }
        }
    }

    companion object {
        /** location updates should fire approximately every second  */
        private const val LOCATION_UPDATE_MIN_TIME_GPS = 1000

        /** location updates should fire, even if last signal is same than current one (0m distance to last location is OK)  */
        private const val LOCATION_UPDATE_DISTANCE_GPS = 0

        /** location updates should fire approximately every second  */
        private const val LOCATION_UPDATE_MIN_TIME_NW = 1000

        /** location updates should fire, even if last signal is same than current one (0m distance to last location is OK)  */
        private const val LOCATION_UPDATE_DISTANCE_NW = 0

        /** to faster access location, even use 10 minute old locations on start-up  */
        private const val LOCATION_OUTDATED_WHEN_OLDER_MS = 1000 * 60 * 10
    }

    init {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        this.locationListener = locationListener
        gpsProviderEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        networkProviderEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}