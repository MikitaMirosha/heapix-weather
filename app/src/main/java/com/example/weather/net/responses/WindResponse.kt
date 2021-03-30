package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class WindResponse(
    val speed: BigDecimal? = null,

    @SerializedName("deg")
    val windDirectionDegree: Int? = null
)