package com.example.weather.net.responses

data class TotalWeatherResponse(
    val cod: String? = null,
    val message: Int? = null,
    val cnt: Int? = null,
    val list: MutableList<WeatherListResponse> = mutableListOf(),
    val city: CityResponse? = null
)



