package com.challenge.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.networking.IoDispatcher
import com.challenge.networking.data.toErrorBody
import com.challenge.weather.data.CityRepository
import com.challenge.weather.data.WeatherRepository
import com.challenge.weather.model.City
import com.challenge.weather.model.CurrentWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeWeatherViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeWeatherUiState>(HomeWeatherUiState.Start)
    val uiState: StateFlow<HomeWeatherUiState> = _uiState

    private val cityNameInputFlow = MutableStateFlow("")

    init {
        observeForSavedCity()
        observeForCityInput()
    }

    fun onNewCityNameInput(cityName: String) {
        cityNameInputFlow.value = cityName
    }

    fun onCitySelected(currentWeather: CurrentWeather) {
        viewModelScope.launch {
            val city = currentWeather.toCity()
            cityRepository.saveCity(city)
        }
    }

    fun reload() {
        searchForCity(cityNameInputFlow.value)
    }

    private fun searchForCity(cityName: String) {
        viewModelScope.launch {
            weatherRepository.fetchCurrentWeather(City(cityName))
                .onStart { mutate(HomeWeatherUiState.Loading) }
                .catch { error ->
                    val errorBody = error.toErrorBody()
                    val state = if (errorBody.error.code == ERROR_CODE_NO_MATCHING_LOCATION_FOUND) {
                        HomeWeatherUiState.NoMatchingLocation(errorBody.error)
                    } else {
                        HomeWeatherUiState.Error
                    }
                    mutate(state)
                }
                .onEach { Log.i(TAG, "searchForCity: $it") }
                .map(HomeWeatherUiState::SearchResult)
                .collect(::mutate)
        }
    }

    private fun observeForSavedCity() {
        cityRepository.savedCity
            .onEach { if (it == null) mutate(HomeWeatherUiState.Empty) }
            .filterNotNull()
            .flatMapMerge(transform = weatherRepository::fetchCurrentWeather)
            .catch { error -> mutate(HomeWeatherUiState.Empty) }
            .map(HomeWeatherUiState::CitySelected)
            .onEach(::mutate)
            .launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeForCityInput() {
        cityNameInputFlow
            .filter(String::isNotBlank)
            .debounce(SEARCH_INPUT_DEBOUNCE)
            .onEach(::searchForCity)
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

    private fun mutate(state: HomeWeatherUiState) {
        _uiState.update { state }
    }

    companion object {
        private const val TAG = "HomeWeatherViewModel"
        private const val SEARCH_INPUT_DEBOUNCE = 1500L
        private const val ERROR_CODE_NO_MATCHING_LOCATION_FOUND = 1006
    }
}