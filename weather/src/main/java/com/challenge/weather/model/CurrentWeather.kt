package com.challenge.weather.model

data class CurrentWeather(
    val locationName: String,
    val condition: String,
    val conditionIconUrl: String,
    val tempCelsius: Double,
    val humidity: Double,
    val feelsLikeCelsius: Double,
    val uv: Double,
) {

    val tempCelsiusDisplayFormat: String = tempCelsius.toInt().toString()
    val humidityDisplayFormat: String = humidity.toInt().toString()
    val feelsLikeCelsiusDisplayFormat: String = feelsLikeCelsius.toInt().toString()
    val uvDisplayFormat: String = uv.toInt().toString()

    fun toCity() = City(locationName)
}
