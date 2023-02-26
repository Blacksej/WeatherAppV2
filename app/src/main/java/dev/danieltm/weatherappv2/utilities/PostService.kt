package dev.danieltm.weatherappv2.utilities

import dev.danieltm.weatherappv2.Models.WeatherController
import dev.danieltm.weatherappv2.Models.WeatherModelResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface PostService {

    suspend fun getPosts(lat: String, long: String): WeatherModelResponse.Welcome

    companion object{
        fun create() : PostService{
            return WeatherController(
                client = HttpClient(Android){
                    install(ContentNegotiation){
                        json(Json{
                            prettyPrint = true
                            isLenient = true
                        })
                    }
                }
            )
        }
    }
}