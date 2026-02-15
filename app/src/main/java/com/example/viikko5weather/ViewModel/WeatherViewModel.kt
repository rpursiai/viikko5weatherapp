package com.example.viikko5weather.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viikko5weather.data.model.WeatherResponse
import com.example.viikko5weather.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeatherUiState(
    val city: String = "",
    val isLoading: Boolean = false,
    val result: WeatherResponse? = null,
    val error: String? = null
)

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    fun updateCity(newCity: String) {
        _uiState.update { it.copy(city = newCity, error = null) }
    }

    fun fetchWeather(apiKey: String) {
        val city = _uiState.value.city.trim()
        if (city.isEmpty()) {
            _uiState.update { it.copy(error = "Please enter a city name.") }
            return
        }
        if (apiKey.isBlank()) {
            _uiState.update { it.copy(error = "Missing API key (BuildConfig.OPENWEATHER_API_KEY is empty).") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, result = null) }
            try {
                val response = RetrofitInstance.api.getWeatherByCity(city = city, apiKey = apiKey)
                _uiState.update { it.copy(isLoading = false, result = response) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Something went wrong."
                    )
                }
            }
        }
    }
}
