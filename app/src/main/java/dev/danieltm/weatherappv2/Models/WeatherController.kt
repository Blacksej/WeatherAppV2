package dev.danieltm.weatherappv2.Models

import dev.danieltm.weatherappv2.utilities.HttpRoutes
import dev.danieltm.weatherappv2.utilities.PostService
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlin.text.get

class WeatherController(
    // Dependency injection
    private val client: HttpClient
): PostService{

    override suspend fun getPosts(lat: String, long: String): WeatherModelResponse.Welcome {
        return try{
            var get = client.get { url(HttpRoutes.getWeatherFromLatAndLong(lat, long)) }
            var body = get.body<WeatherModelResponse.Welcome>()
            println(body.main?.temp.toString())
            body

        } catch (e: RedirectResponseException){
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            WeatherModelResponse.Welcome()
        }catch (e: ClientRequestException){
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            WeatherModelResponse.Welcome()
        }catch (e: ServerResponseException){
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            WeatherModelResponse.Welcome()
        }catch (e: Exception){
            // 3xx - responses
            println("Error: ${e.message}")
            WeatherModelResponse.Welcome()
        }
    }

}