package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class WeatherListResponse (
    @SerializedName("dt")       val dateTime:                       Long? = null,
    @SerializedName("main")     val mainParametersResponse:         MainParametersResponse? = null,
    @SerializedName("weather")  val weatherResponses:               MutableList<WeatherResponse> = mutableListOf(),
    @SerializedName("clouds")   val cloudsResponse:                 CloudsResponse? = null,
    @SerializedName("wind")     val windResponse:                   WindResponse? = null,

    val visibility: Int? = null,

    @SerializedName("pop")      val probabilityOfPrecipitation:     BigDecimal? = null,
    @SerializedName("sys")      val systemResponse:                 SystemResponse? = null,
    @SerializedName("dt_txt")   val dateTimeTxt:                    String? = null
)