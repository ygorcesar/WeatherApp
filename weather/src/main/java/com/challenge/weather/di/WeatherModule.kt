package com.challenge.weather.di

import com.challenge.weather.data.CityRepository
import com.challenge.weather.data.WeatherRepository
import com.challenge.weather.data.local.CityDataStore
import com.challenge.weather.data.remote.WeatherRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    fun provideCityRepository(
        repository: CityDataStore,
    ): CityRepository = repository

    @Provides
    fun provideWeatherRepository(
        repository: WeatherRemoteRepository,
    ): WeatherRepository = repository
}