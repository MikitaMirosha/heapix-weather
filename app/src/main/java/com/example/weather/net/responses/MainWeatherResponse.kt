package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class MainWeatherResponse (
    val temp: BigDecimal? = null,

    @SerializedName("feels_like")   val feelsLike:  BigDecimal? = null,
    @SerializedName("temp_min")     val tempMin:    BigDecimal? = null,
    @SerializedName("temp_max")     val tempMax:    BigDecimal? = null,

    val pressure: Int? = null,

    @SerializedName("sea_level")    val seaLevel:   Int? = null,
    @SerializedName("grnd_level")   val groundLevel: Int? = null,

    val humidity: Int? = null,

    @SerializedName("temp_kf")      val tempKf: Double? = null
)