package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName

data class TotalWeatherResponse(
    @SerializedName("cod")      val code:                   String? = null,

    val message: Int? = null,

    @SerializedName("cnt")      val numberOfTimestamps:     Int? = null,
    @SerializedName("list")     val weatherListResponse:    MutableList<WeatherListResponse> = mutableListOf(),
    @SerializedName("city")     val cityResponse:           CityResponse? = null
)