package com.example.weather.net.services

import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET
    fun getTotalWeatherResponse(): Observable<TotalWeatherResponse>
}