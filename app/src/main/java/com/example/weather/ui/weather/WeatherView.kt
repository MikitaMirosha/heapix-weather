package com.example.weather.ui.weather

import com.example.weather.base.BaseMvpView
import com.example.weather.net.responses.WeatherListResponse
import org.joda.time.DateTime
import java.math.BigDecimal

interface WeatherView : BaseMvpView {
    fun setupWeatherItems(weatherListResponse: MutableList<WeatherListResponse>)

    fun setupCityName(name: String)
    fun setupWeatherConditionIcon(icon: String)
    fun setupTemperature(temperature: BigDecimal)
    fun setupWeatherDescription(description: String)

    fun setupTemperatureFeelsLike(temperatureFeelsLike: BigDecimal)
    fun setupPressure(pressure: Int)
    fun setupHumidity(humidity: Int)
    fun setupWindSpeed(speed: BigDecimal)
    fun setupSunrise(sunrise: String)
    fun setupSunset(sunset: String)
    fun setupDateTime(dateTime: String)

    fun toggleFutureWeatherList()
}