package com.example.viikko5weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.viikko5weather.BuildConfig
import com.example.viikko5weather.ui.WeatherScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherScreen(apiKey = BuildConfig.OPENWEATHER_API_KEY)
        }
    }
}
