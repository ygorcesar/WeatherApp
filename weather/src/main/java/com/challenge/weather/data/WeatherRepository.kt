package com.challenge.weather.data

import com.challenge.weather.model.City
import com.challenge.weather.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun fetchCurrentWeather(city: City): Flow<CurrentWeather>
}