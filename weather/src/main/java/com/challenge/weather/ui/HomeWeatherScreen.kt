package com.challenge.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.challenge.components.progress.LoadingScreen
import com.challenge.components.style.CardPrimaryColor
import com.challenge.components.style.Sizes
import com.challenge.components.text.ColumnItemLabel
import com.challenge.components.text.EmptyContent
import com.challenge.components.text.ErrorScreen
import com.challenge.components.text.SearchTextField
import com.challenge.components.weather.WeatherTemperature
import com.challenge.weather.R
import com.challenge.weather.model.CurrentWeather

@Composable
fun HomeWeatherScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeWeatherViewModel = viewModel()
    val uiModel by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .padding(Sizes.spaceNormal)
    ) {
        HomeWeatherSearchBar(
            onNewText = viewModel::onNewCityNameInput,
        )

        when (val state = uiModel) {
            is HomeWeatherUiState.CitySelected -> CitySelectedState(state)

            is HomeWeatherUiState.Empty -> EmptyContent(
                title = stringResource(R.string.empty_state_title),
                description = stringResource(R.string.empty_state_description),
            )

            is HomeWeatherUiState.NoMatchingLocation -> EmptyContent(
                title = state.errorBodyContent.message,
                description = stringResource(R.string.empty_state_description),
            )

            is HomeWeatherUiState.Error -> ErrorScreen(
                title = stringResource(R.string.error_state_title),
                description = stringResource(R.string.error_state_description),
                buttonText = stringResource(R.string.error_state_try_again),
                onRetry = viewModel::reload,
            )

            is HomeWeatherUiState.Loading -> LoadingScreen()

            is HomeWeatherUiState.SearchResult -> SearchResultState(
                state = state,
                onResultClick = viewModel::onCitySelected
            )

            is HomeWeatherUiState.Start -> Unit /* No-Op */
        }
    }
}

@Composable
private fun HomeWeatherSearchBar(
    onNewText: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val textValue = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(textValue.value) { onNewText.invoke(textValue.value.text) }

    SearchTextField(
        text = textValue,
        placeholderText = stringResource(R.string.search_location_placeholder),
        modifier = modifier,
    )
}

@Composable
private fun SearchResultState(
    state: HomeWeatherUiState.SearchResult,
    onResultClick: (CurrentWeather) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardPrimaryColor),
        modifier = modifier
            .padding(top = Sizes.spaceNormal)
            .fillMaxWidth()
            .clickable { onResultClick.invoke(state.currentWeather) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = Sizes.spaceXNormal, vertical = Sizes.spaceNormal)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = state.currentWeather.locationName,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.height(Sizes.spaceXSmall))

                WeatherTemperature(
                    temperature = state.currentWeather.tempCelsiusDisplayFormat,
                    style = MaterialTheme.typography.displayMedium,
                )
            }

            Spacer(Modifier.weight(1f))

            AsyncImage(
                model = state.currentWeather.conditionIconUrl,
                contentDescription = state.currentWeather.condition,
                modifier = Modifier.size(Sizes.iconSizeMedium)
            )
        }
    }
}

@Composable
private fun CitySelectedState(
    state: HomeWeatherUiState.CitySelected,
    modifier: Modifier = Modifier,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(Sizes.spaceNormal),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(Sizes.spaceNormal)
            .fillMaxSize()
    ) {
        AsyncImage(
            model = state.currentWeather.conditionIconUrl,
            contentDescription = state.currentWeather.condition,
            modifier = Modifier.size(Sizes.iconSizeLarge)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = state.currentWeather.locationName,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.width(Sizes.spaceXSmall))
            Icon(
                painter = painterResource(com.challenge.components.R.drawable.ic_location),
                contentDescription = "icon location",
                modifier = Modifier.size(Sizes.iconSizeSmall),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        WeatherTemperature(
            temperature = state.currentWeather.tempCelsiusDisplayFormat,
        )

        Spacer(Modifier.height(Sizes.spaceNormal))

        WeatherStatusCard(state = state)
    }
}

@Composable
private fun WeatherStatusCard(
    state: HomeWeatherUiState.CitySelected,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardPrimaryColor),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Sizes.spaceLarge),
            modifier = modifier
                .padding(Sizes.spaceNormal)
        ) {
            ColumnItemLabel(
                title = stringResource(R.string.weather_status_humidity),
                value = stringResource(
                    R.string.weather_status_humidity_value,
                    state.currentWeather.humidityDisplayFormat,
                ),
            )

            ColumnItemLabel(
                title = stringResource(R.string.weather_status_uv),
                value = state.currentWeather.uvDisplayFormat,
            )

            ColumnItemLabel(
                title = stringResource(R.string.weather_status_feels_like),
                value = stringResource(
                    R.string.weather_status_feels_like_value,
                    state.currentWeather.feelsLikeCelsiusDisplayFormat,
                ),
            )
        }
    }
}
