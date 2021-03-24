package com.example.weather.net.responses

data class TotalWeatherResponse(
    val coord: CoordinatesResponse? = null,
    val weather: MutableList<WeatherResponse> = mutableListOf(),
    val base: String? = null,
    val main: MainWeatherResponse? = null,
    val visibility: Int? = null,
    val wind: WindResponse? = null,
    val clouds: CloudsResponse? = null,
    val dt: Long? = null,
    val sys: SystemResponse? = null,
    val timezone: Long? = null,
    val id: Long? = null,
    val name: String? = null,
    val cod: Int? = null
)


