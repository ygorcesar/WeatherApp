package com.challenge.weather.data.remote

import com.challenge.weather.data.remote.model.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("v1/current.json")
    suspend fun fetchCurrentWeather(
        @Query("q") cityName: String,
    ): CurrentWeatherResponse
}