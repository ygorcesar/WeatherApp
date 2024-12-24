package com.challenge.weather.data

import com.challenge.weather.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun saveCity(city: City)
    val savedCity: Flow<City?>
}