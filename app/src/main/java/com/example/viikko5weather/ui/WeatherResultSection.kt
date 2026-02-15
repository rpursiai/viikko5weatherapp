package com.example.viikko5weather.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale
import coil.compose.AsyncImage


@Composable
fun WeatherResultSection(
    city: String,
    tempC: Double,
    description: String,
    iconCode: String
) {
    val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        AsyncImage(
            model = iconUrl,
            contentDescription = description
        )

        Text(text = city, style = MaterialTheme.typography.titleLarge)
        Text(text = "${"%.1f".format(tempC)} °C",
            style = MaterialTheme.typography.displaySmall)
        Text(text = description)
    }
}
