package dev.danieltm.weatherappv2.Models

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.collections.ArrayList

class LocationController {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latAndLong: ArrayList<String> = ArrayList<String>()

    fun getLocation(
        activity: Activity,
        context: Context,
        callback: (arrayList: ArrayList<String>) -> Unit
    ) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)

        // Check location permission
        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }

        // get latitude and longitude
        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if(it!=null){
                latAndLong.add(it.latitude.toString())
                latAndLong.add(it.longitude.toString())
            }
            callback(latAndLong)
        }
    }


    fun getCityName(lat: Double, long: Double, context: Context):String{
        var cityName: String?
        val geoCoder = Geocoder(context, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 1)

        cityName = address?.get(0)?.adminArea

        if(cityName == null){
            cityName = address?.get(0)?.locality
            if(cityName == null){
                cityName = address?.get(0)?.subAdminArea
            }
        }
        return cityName ?: ""
    }
}