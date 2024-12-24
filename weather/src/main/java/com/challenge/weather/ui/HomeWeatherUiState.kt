package com.challenge.weather.ui

import com.challenge.networking.data.ErrorBodyContent
import com.challenge.weather.model.CurrentWeather

sealed interface HomeWeatherUiState {
    data class CitySelected(val currentWeather: CurrentWeather) : HomeWeatherUiState
    data class SearchResult(val currentWeather: CurrentWeather) : HomeWeatherUiState
    data object Empty : HomeWeatherUiState
    data object Error : HomeWeatherUiState
    data class NoMatchingLocation(val errorBodyContent: ErrorBodyContent) : HomeWeatherUiState
    data object Loading : HomeWeatherUiState
    data object Start : HomeWeatherUiState
}

