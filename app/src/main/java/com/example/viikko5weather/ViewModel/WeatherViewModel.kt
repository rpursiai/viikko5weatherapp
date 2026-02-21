package com.example.viikko5weather.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.viikko5weather.data.model.WeatherEntity
import com.example.viikko5weather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeatherUiState(
    val cityInput: String = "",
    val isLoading: Boolean = false,
    val cached: WeatherEntity? = null,
    val error: String? = null
)

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.observeLatest().collect { entity ->
                _uiState.update { it.copy(cached = entity) }
            }
        }
    }

    fun updateCity(newCity: String) {
        _uiState.update { it.copy(cityInput = newCity, error = null) }
    }

    fun fetchWeather() {
        val city = _uiState.value.cityInput.trim()
        if (city.isEmpty()) {
            _uiState.update { it.copy(error = "Please enter a city name.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.refreshIfNeeded(city = city, maxAgeMinutes = 30)
            } catch (t: Throwable) {
                _uiState.update { it.copy(error = t.message ?: "Request failed.") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}

class WeatherViewModelFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}