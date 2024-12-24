package com.challenge.weather.di

import com.challenge.weather.data.remote.WeatherAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherNetworkModule {

    @Provides
    @Singleton
    fun provideWeatherAPI(
        retrofit: Retrofit,
    ): WeatherAPI = retrofit.create(WeatherAPI::class.java)
}