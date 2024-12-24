package com.challenge.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.challenge.components.R
import com.challenge.components.style.CardPrimaryColor
import com.challenge.components.style.TextSecondaryLight
import com.challenge.components.style.WeatherTrackerTheme

@Composable
fun SearchTextField(
    text: MutableState<TextFieldValue>,
    placeholderText: String,
    modifier: Modifier = Modifier,
    trailingIconResId: Int = R.drawable.ic_search,
) {
    OutlinedTextField(
        value = text.value,
        placeholder = {
            Text(
                text = placeholderText,
                color = TextSecondaryLight,
            )
        },
        onValueChange = { value -> text.value = value },
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
        singleLine = true,
        trailingIcon = {
            Icon(
                painter = painterResource(trailingIconResId),
                contentDescription = "search icon",
                tint = TextSecondaryLight
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
        ),
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(CardPrimaryColor)
    )
}

@Preview
@Composable
private fun SearchTextFieldPreview() = WeatherTrackerTheme {
    val text = remember { mutableStateOf(TextFieldValue("California")) }
    SearchTextField(
        text = text,
        placeholderText = "Search location",
    )
}