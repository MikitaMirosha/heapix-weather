package com.example.weather.net.repo

import android.content.SharedPreferences
import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.net.responses.WeatherListResponse
import com.example.weather.net.services.WeatherService
import com.example.weather.utils.preferences.description
import io.reactivex.Observable

class WeatherRepo(
    private val api: WeatherService,
    private val sharedPreferences: SharedPreferences
) {

    fun getTotalWeatherResponse(): Observable<TotalWeatherResponse> {
        return api.getTotalWeatherResponse()
    }

//    fun getBaseCode(): String = sharedPreferences.baseCode
//
//    fun saveBaseCode(baseCode: String) {
//        sharedPreferences.baseCode = baseCode
//    }
//
//    fun isBaseCodeInStorage(): Boolean = getBaseCode().isNotEmpty()

    fun getDescription() = sharedPreferences.description

    fun saveDescription(description: String) {
        sharedPreferences.description = description
    }
}