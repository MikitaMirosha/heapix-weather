//package com.example.weather.utils.extensions
//
//import android.Manifest
//import android.content.Context
//import android.content.pm.PackageManager
//import android.location.Location
//import android.location.LocationManager
//import androidx.core.app.ActivityCompat
//
//class Location {
//
//    fun getLastKnownLocation(enabledProvidersOnly: Boolean, context: Context): Location? {
//        val manager =
//            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        var utilLocation: Location? = null
//        val providers =
//            manager.getProviders(enabledProvidersOnly)
//        for (provider in providers) {
//            if (ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//            utilLocation = manager.getLastKnownLocation(provider!!)
//            if (utilLocation != null) return utilLocation
//        }
//        return null
//    }
//}