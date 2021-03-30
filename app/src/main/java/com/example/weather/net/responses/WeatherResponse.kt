package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val id: Int? = null,

    @SerializedName("main")
    val weatherParametersGroup: String? = null,

    val description: String? = null,
    val icon: String? = null
)