package com.example.weather.net.repo

import android.content.SharedPreferences
import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.net.services.WeatherService
import com.example.weather.utils.preferences.description
import com.example.weather.utils.preferences.pressure
import io.reactivex.Observable

class WeatherRepo(
    private val api: WeatherService,
    private val sharedPreferences: SharedPreferences
) {

    fun getTotalWeatherResponse(): Observable<TotalWeatherResponse> {
        return api.getTotalWeatherResponse()
    }

    fun getDescription() = sharedPreferences.description

    fun saveDescription(description: String) {
        sharedPreferences.description = description
    }

    fun getPressure() = sharedPreferences.pressure

    fun savePressure(pressure: Int) {
        sharedPreferences.pressure = pressure
    }
}