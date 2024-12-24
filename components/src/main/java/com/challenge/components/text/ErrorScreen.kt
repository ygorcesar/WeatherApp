package com.challenge.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.challenge.components.style.Sizes
import com.challenge.components.style.WeatherTrackerTheme

@Composable
fun ErrorScreen(
    title: String,
    description: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Sizes.spaceSmall, Alignment.CenterVertically),
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = description
        )
        Button(onRetry) {
            Text(text = buttonText)
        }
    }
}

@Preview
@Composable
private fun previewErrorScreen() = WeatherTrackerTheme {
    ErrorScreen(
        title = "Error title",
        description = "Something went wrong!",
        buttonText = "Try again",
        onRetry = {}
    )
}