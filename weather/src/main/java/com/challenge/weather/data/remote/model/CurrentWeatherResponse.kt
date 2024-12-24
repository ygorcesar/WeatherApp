package com.challenge.weather.data.remote.model

import com.challenge.weather.model.CurrentWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    @SerialName("location") val location: WeatherLocationResponse?,
    @SerialName("current") val current: CurrentWeatherDataResponse?,
) {

    fun toDomain(): CurrentWeather {
        return CurrentWeather(
            locationName = requireNotNull(location?.name) { "Current location name is required!" },
            condition = requireNotNull(current?.condition?.text) { "Current condition text is required!" },
            conditionIconUrl = requireNotNull(current?.condition?.icon?.mapToUrl()) { "Current condition icon is required!" },
            tempCelsius = requireNotNull(current?.tempCelsius) { "Current temp_c is required!" },
            humidity = requireNotNull(current?.humidity) { "Current humidity is required!" },
            feelsLikeCelsius = requireNotNull(current?.feelsLikeCelsius) { "Current feels_like_c is required!" },
            uv = requireNotNull(current?.uv) { "Current uv weather is required!" }
        )
    }

    private fun String.mapToUrl(): String {
        return if (this.lowercase().contains("https://|http://".toRegex())) {
            this
        } else {
            "https:$this"
        }
    }
}

@Serializable
data class CurrentWeatherDataResponse(
    @SerialName("temp_c") val tempCelsius: Double?,
    @SerialName("humidity") val humidity: Double?,
    @SerialName("feelslike_c") val feelsLikeCelsius: Double?,
    @SerialName("uv") val uv: Double?,
    @SerialName("condition") val condition: CurrentWeatherConditionResponse?,
)

@Serializable
data class CurrentWeatherConditionResponse(
    @SerialName("text") val text: String?,
    @SerialName("icon") val icon: String?,
    @SerialName("code") val code: Int?,
)

@Serializable
data class WeatherLocationResponse(
    @SerialName("name") val name: String?,
)
