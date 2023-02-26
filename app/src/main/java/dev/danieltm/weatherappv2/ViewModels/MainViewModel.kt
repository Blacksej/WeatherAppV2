package dev.danieltm.weatherappv2.ViewModels

import android.app.Activity
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import dev.danieltm.weatherappv2.Models.LocationController
import dev.danieltm.weatherappv2.Models.WeatherModelResponse
import dev.danieltm.weatherappv2.utilities.PostService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class MainViewModel: ViewModel() {

    private val locationController = LocationController()
    private val postService = PostService.create()
    private var weatherResponseRoot = WeatherModelResponse.Welcome()

    private var _temp = MutableStateFlow("")
    var temp = _temp.asStateFlow()

    private var _feelsLike = MutableStateFlow("")
    var feelsLike = _feelsLike.asStateFlow()

    private var _windSpeed = MutableStateFlow("")
    var windSpeed = _windSpeed.asStateFlow()

    private var _windDirection = MutableStateFlow("")
    var windDirection = _windDirection.asStateFlow()

    private var _weatherDescription = MutableStateFlow("")
    var weatherDescription = _weatherDescription.asStateFlow()

    private var _cityName = MutableStateFlow("")
    var cityName = _cityName.asStateFlow()

    private var _latitude = MutableStateFlow("LAT")
    var latitude = _latitude.asStateFlow()

    private var _longitude = MutableStateFlow("LONG")
    var longitude = _longitude.asStateFlow()

    private var _icon = MutableStateFlow("LONG")
    var icon = _icon.asStateFlow()


    fun getCurrentWeather(lat: String, long: String){
        runBlocking {
            launch { weatherResponseRoot = postService.getPosts(lat, long) }
        }
        _temp.value = (weatherResponseRoot.main?.temp?.minus(273.15)?.roundToInt()).toString()
        _feelsLike.value = (weatherResponseRoot.main?.feelsLike?.minus(273.15)?.roundToInt()).toString()
        _windSpeed.value = (weatherResponseRoot.wind?.speed?.roundToInt()).toString()
        _windDirection.value = weatherResponseRoot.wind?.deg.toString()

        if(weatherResponseRoot.weather[0].description.isNotEmpty()){
            _weatherDescription.value = weatherResponseRoot
                .weather[0].description
            _icon.value = weatherResponseRoot.weather[0].icon
        }
    }

    fun getLocationAndWeather(activity: Activity, context: Context){
        locationController.getLocation(activity, context) {list: ArrayList<String> ->
            _latitude.value = list[0]
            _longitude.value = list[1]
            getCity(latitude.value, longitude.value, context)
            getCurrentWeather(list[0], list[1])}
    }

    // Method for setting the cityname variable
    private fun getCity(lat: String, long: String, context: Context){
        _cityName.value = locationController.getCityName(lat.toDouble(), long.toDouble(), context)
    }

}