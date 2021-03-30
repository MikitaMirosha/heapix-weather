package com.example.weather.net.services

import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast?")
    fun getTotalWeatherResponse(
        @Query("q") cityName: String?
    ): Observable<TotalWeatherResponse>
}