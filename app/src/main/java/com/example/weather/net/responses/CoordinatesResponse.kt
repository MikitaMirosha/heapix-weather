package com.example.weather.net.responses

import java.math.BigDecimal

data class CoordinatesResponse(
    val lon: BigDecimal? = null,
    val lat: BigDecimal? = null
)