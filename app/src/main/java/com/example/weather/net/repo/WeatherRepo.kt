package com.example.weather.net.repo

import android.content.SharedPreferences
import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.net.services.WeatherService
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
}