package com.example.weather.net.services

import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface WeatherService {

    @GET("forecast?q=Minsk&APPID=ea16b9eb090a4c931a74d2718ef4e161")
    fun getTotalWeatherResponse(): Observable<TotalWeatherResponse>
}