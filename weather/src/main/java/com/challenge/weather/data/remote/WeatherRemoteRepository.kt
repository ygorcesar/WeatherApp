package com.challenge.weather.data.remote

import com.challenge.networking.IoDispatcher
import com.challenge.weather.data.WeatherRepository
import com.challenge.weather.data.remote.model.CurrentWeatherResponse
import com.challenge.weather.model.City
import com.challenge.weather.model.CurrentWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRemoteRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherAPI: WeatherAPI,
) : WeatherRepository {

    override fun fetchCurrentWeather(city: City): Flow<CurrentWeather> {
        return flow { emit(weatherAPI.fetchCurrentWeather(city.name)) }
            .map(CurrentWeatherResponse::toDomain)
            .flowOn(ioDispatcher)
    }
}