package com.example.weather.utils.extensions

import com.example.weather.net.responses.WeatherListResponse

fun WeatherListResponse.getWeatherConditionIcon(): String? {
    this.weatherResponses.map { weatherResponse ->
        weatherResponse.icon
    }.map { icon ->
        return icon
    }
    return null
}

fun WeatherListResponse.getConvertedTemperature(): String? {
    return this.mainParametersResponse?.temperature?.let { kelvinDegrees ->
        convertKelvinToCelsius(kelvinDegrees).toPlainString()
    }
}

fun WeatherListResponse.getConvertedDateTime(): String? {
    return this.dateTime.let { dateTime ->
        dateTime?.let { posixDateTime ->
            convertPosixFormatToUtcDateTime(posixDateTime)
        }
    }
}

fun WeatherListResponse.getWeatherDescription(): String? {
    this.weatherResponses.map { weatherResponse ->
        weatherResponse.description
    }.map { description ->
        return description
    }
    return null
}

fun WeatherListResponse.getConvertedTemperatureFeelsLike(): String? {
    return this.mainParametersResponse?.feelsLike.let { temperatureFeelsLike ->
        temperatureFeelsLike?.let { kelvinDegrees ->
            convertKelvinToCelsius(kelvinDegrees).toPlainString()
        }
    }
}

fun WeatherListResponse.getWindSpeed(): String {
    return this.windResponse?.speed.toString()
}