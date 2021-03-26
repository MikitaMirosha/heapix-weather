package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName

data class SystemResponse(
    @SerializedName("pod")
    val partOfTheDay: String? = null
)