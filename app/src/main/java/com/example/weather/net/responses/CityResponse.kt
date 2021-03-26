package com.example.weather.net.responses

data class CityResponse(
    val id: Int? = null,
    val name: String? = null,
    val coord: CoordinatesResponse? = null,
    val country: String? = null,
    val population: Long? = null,
    val timezone: Long? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)