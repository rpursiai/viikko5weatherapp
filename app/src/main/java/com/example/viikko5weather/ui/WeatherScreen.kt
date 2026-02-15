package com.example.viikko5weather.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko5weather.ViewModel.WeatherViewModel

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color


@Composable
fun WeatherScreen(
    apiKey: String,
    vm: WeatherViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAD5CF))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = state.city,
            onValueChange = vm::updateCity,
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = { vm.fetchWeather(apiKey) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch weather")
        }

        when {
            state.isLoading -> {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Text(text = state.error ?: "", color = MaterialTheme.colorScheme.error)
            }

            state.result != null -> {
                WeatherResultSection(
                    city = state.result!!.name,
                    tempC = state.result!!.main.temp,
                    description = state.result!!.weather.firstOrNull()?.description.orEmpty(),
                    iconCode = state.result!!.weather.firstOrNull()?.icon.orEmpty()
                )
            }
        }
    }
}
