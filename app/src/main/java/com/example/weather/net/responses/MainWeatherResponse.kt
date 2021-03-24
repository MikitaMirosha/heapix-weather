package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName

data class MainWeatherResponse (
    val temp: Double? = null,

    @SerializedName("feels_like")   val feelsLike:  Double? = null,
    @SerializedName("temp_min")     val tempMin:    Double? = null,
    @SerializedName("temp_max")     val tempMax:    Double? = null,

    val pressure: Int? = null,
    val humidity: Int? = null
)