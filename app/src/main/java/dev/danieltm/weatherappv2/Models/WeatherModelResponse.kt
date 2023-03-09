package dev.danieltm.weatherappv2.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class WeatherModelResponse {
    @Serializable
    data class Welcome (
        val coord: Coord? = null,
        val weather: List<Weather> = emptyList(),
        val base: String = "",
        val main: Main? = null,
        val visibility: Long = 2,
        val wind: Wind? = null,
        val clouds: Clouds? = null,
        val dt: Long = 2,
        val sys: Sys? = null,
        val timezone: Long = 2,
        val id: Long = 2,
        val name: String = "",
        val cod: Long = 2
    )

    @Serializable
    data class Clouds (
        val all: Long = 11111
    )

    @Serializable
    data class Coord (
        val lon: Double = 1.1,
        val lat: Double = 1.1
    )

    @Serializable
    data class Main (
        val temp: Double = 1.1,

        @SerialName("feels_like")
        val feelsLike: Double = 1.1,

        @SerialName("temp_min")
        val tempMin: Double = 1.1,

        @SerialName("temp_max")
        val tempMax: Double = 1.1,

        val pressure: Long = 11111,
        val humidity: Long = 11111,

        @SerialName("sea_level")
        val seaLevel: Long = 11111,

        @SerialName("grnd_level")
        val grndLevel: Long = 11111
    )

    @Serializable
    data class Sys (
        val type: Long = 0,
        val id: Long = 0,
        val country: String = "",
        val sunrise: Long = 11111,
        val sunset: Long = 11111
    )

    @Serializable
    data class Weather (
        val id: Long = 11111,
        val main: String = "",
        val description: String = "",
        val icon: String = ""
    )

    @Serializable
    data class Wind (
        val speed: Double = 1.1,
        val deg: Long = 11111,
        val gust: Double = 1.1
    )
}