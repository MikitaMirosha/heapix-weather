package com.example.weather.net.responses

import java.math.BigDecimal

data class WindResponse(
    val speed: BigDecimal? = null,
    val deg: Int? = null
)