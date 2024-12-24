package com.challenge.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.challenge.components.style.CardPrimaryColor
import com.challenge.components.style.Sizes
import com.challenge.components.style.TextSecondary
import com.challenge.components.style.TextSecondaryLight
import com.challenge.components.style.WeatherTrackerTheme

@Composable
fun ColumnItemLabel(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondaryLight,
        )

        Spacer(Modifier.height(Sizes.spaceTiny))

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
        )
    }
}

@Preview
@Composable
private fun previewColumnItemLabel() = WeatherTrackerTheme {
    Card(
        shape = CircleShape.copy(
            CornerSize(16.dp)
        ),
        colors = CardDefaults.cardColors()
            .copy(containerColor = CardPrimaryColor)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Sizes.spaceLarge),
            modifier = Modifier
                .padding(Sizes.spaceNormal)
        ) {
            ColumnItemLabel(
                title = "Title",
                value = "Value",
            )

            ColumnItemLabel(
                title = "Title",
                value = "Value",
            )

            ColumnItemLabel(
                title = "Title",
                value = "Value",
            )
        }
    }
}