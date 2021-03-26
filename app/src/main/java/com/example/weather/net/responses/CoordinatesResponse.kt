package com.example.weather.net.responses

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CoordinatesResponse(
    @SerializedName("lon")  val longitude:  BigDecimal? = null,
    @SerializedName("lat")  val latitude:   BigDecimal? = null
)