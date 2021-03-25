package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class WeatherListResponse (
    val dt: Long? = null,
    val main: MainWeatherResponse? = null,
    val weather: MutableList<WeatherResponse> = mutableListOf(),
    val clouds: CloudsResponse? = null,
    val wind: WindResponse? = null,
    val visibility: Int? = null,
    val pop: BigDecimal? = null,
    val sys: SystemResponse? = null,

    @SerializedName("dt_txt")   val dtTxt:  String? = null
)