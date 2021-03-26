package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MainParametersResponse (
    @SerializedName("temp")         val temperature:                BigDecimal? = null,
    @SerializedName("feels_like")   val feelsLike:                  BigDecimal? = null,
    @SerializedName("temp_min")     val minTemperature:             BigDecimal? = null,
    @SerializedName("temp_max")     val maxTemperature:             BigDecimal? = null,

    val pressure: Int? = null,

    @SerializedName("sea_level")    val seaLevel:                   Int? = null,
    @SerializedName("grnd_level")   val groundLevel:                Int? = null,

    val humidity: Int? = null,

    @SerializedName("temp_kf")      val freezingPointTemperature:   Double? = null
)