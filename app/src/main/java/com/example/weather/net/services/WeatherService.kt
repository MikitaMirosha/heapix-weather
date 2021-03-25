package com.example.weather.net.services

import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface WeatherService {

    @GET("forecast?q=Minsk")
    fun getTotalWeatherResponse(): Observable<TotalWeatherResponse>
}