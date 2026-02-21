package com.example.viikko5weather.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.viikko5weather.data.local.AppDatabase
import com.example.viikko5weather.data.remote.RetrofitInstance
import com.example.viikko5weather.data.repository.WeatherRepository
import com.example.viikko5weather.ViewModel.WeatherViewModel
import com.example.viikko5weather.ViewModel.WeatherViewModelFactory

@Composable
fun WeatherScreen(apiKey: String) {
    val context = LocalContext.current

    val db = remember(context) { buildDb(context) }
    val repository = remember(db, apiKey) {
        WeatherRepository(
            dao = db.weatherDao(),
            api = RetrofitInstance.api,
            apiKey = apiKey
        )
    }

    val vm: WeatherViewModel = viewModel(factory = WeatherViewModelFactory(repository))
    val state by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDAD5CF))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        OutlinedTextField(
            value = state.cityInput,
            onValueChange = vm::updateCity,
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = vm::fetchWeather,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch weather")
        }

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        state.cached?.let { cached ->
            WeatherResultSection(
                city = cached.city,
                tempC = cached.tempC,
                description = cached.description,
                iconCode = cached.icon
            )
        }
    }
}

private fun buildDb(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()