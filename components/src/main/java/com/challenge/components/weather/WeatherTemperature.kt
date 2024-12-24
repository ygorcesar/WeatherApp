package com.challenge.components.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.challenge.components.R
import com.challenge.components.style.Sizes
import com.challenge.components.style.WeatherTrackerTheme

@Composable
fun WeatherTemperature(
    temperature: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.displayLarge,
) {
    Row(modifier = modifier) {
        Text(
            text = temperature,
            style = style,
        )

        Spacer(Modifier.width(Sizes.spaceTiny))

        Text(
            text = stringResource(R.string.temperature_indicator),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun previewWeatherTemperature() = WeatherTrackerTheme {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(Sizes.spaceNormal)
    ) {
        WeatherTemperature("35")
    }
}