package com.example.weather.net.repo

import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.net.services.WeatherService
import io.reactivex.Observable

class WeatherRepo(private val api: WeatherService) {

    fun getTotalWeatherResponse(cityName: String): Observable<TotalWeatherResponse> {
        return api.getTotalWeatherResponse(cityName)
    }
}