package dev.danieltm.weatherappv2.utilities

object HttpRoutes {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather?"
    private const val API_KEY = "f1e67f5406a03b5aad692e22031dc712"

    fun getWeatherFromLatAndLong(lat: String, long: String):String{
        val GET_WEATHER = "${BASE_URL}lat=$lat&lon=$long&lang=en&appid=${API_KEY}&units=metric"
        return GET_WEATHER
    }

    fun getWeatherFromSearch(city: String):String{
        val GET_WEATHER_CITY = "${BASE_URL}q=$city&lang=en&appid=${API_KEY}&units=metric"
        return GET_WEATHER_CITY
    }
}