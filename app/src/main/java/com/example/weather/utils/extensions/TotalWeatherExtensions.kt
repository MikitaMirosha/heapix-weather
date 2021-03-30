package com.example.weather.utils.extensions

import com.example.weather.net.responses.TotalWeatherResponse
import java.math.BigDecimal

fun TotalWeatherResponse.getCity(): String {
    return this.cityResponse?.name ?: ""
}

fun TotalWeatherResponse.getWeatherConditionIcon(): String? {
    this.weatherListResponse.map { weatherListResponse ->
        weatherListResponse.weatherResponses.map { weatherResponse ->
            weatherResponse.icon
        }
    }.map { weatherListResponse ->
        weatherListResponse.map { icon ->
            return icon
        }
    }
    return null
}

fun TotalWeatherResponse.getConvertedTemperature(): BigDecimal? {
    this.weatherListResponse.map { weatherListResponse ->
        weatherListResponse.mainParametersResponse?.temperature
    }.let { temperatureList ->
        temperatureList.map { temperature ->
            return temperature?.let { kelvinDegrees ->
                convertKelvinToCelsius(kelvinDegrees)
            }
        }
    }
    return BigDecimal(0.0)
}

fun TotalWeatherResponse.getWeatherDescription(): String? {
    this.weatherListResponse.map { weatherListResponse ->
        weatherListResponse.weatherResponses.map { weatherResponse ->
            weatherResponse.description
        }.let { descriptionList ->
            descriptionList.map { description ->
                return description
            }
        }
    }
    return null
}

fun TotalWeatherResponse.getConvertedTemperatureFeelsLike(): BigDecimal? {
    this.weatherListResponse.map { weatherListResponse ->
        weatherListResponse.mainParametersResponse?.feelsLike
    }.let { temperatureFeelsLikeList ->
        temperatureFeelsLikeList.map { temperatureFeelsLike ->
            temperatureFeelsLike?.let { kelvinDegrees ->
                return convertKelvinToCelsius(kelvinDegrees)
            }
        }
    }
    return null
}

fun TotalWeatherResponse.getPressure(): Int? {
    this.weatherListResponse.map { weatherListResponse ->
        weatherListResponse.mainParametersResponse?.pressure
    }.let { pressureList ->
        pressureList.map { pressure ->
            return pressure
        }
    }
    return 0
}

fun TotalWeatherResponse.getHumidity(): Int? {
    this.weatherListResponse.map { weatherListResponse ->
        weatherListResponse.mainParametersResponse?.humidity
    }.let { humidityList ->
        humidityList.map { humidity ->
            return humidity
        }
    }
    return 0
}

fun TotalWeatherResponse.getWindSpeed(): BigDecimal? {
    this.weatherListResponse.map { weatherListResponse ->
        weatherListResponse.windResponse?.speed
    }.let { windList ->
        windList.map { wind ->
            return wind
        }
    }
    return BigDecimal(0.0)
}

fun TotalWeatherResponse.getConvertedSunriseTime(): String? {
    this.cityResponse?.sunrise?.let {
        return convertPosixFormatToUtcTime(it)
    }
    return null
}

fun TotalWeatherResponse.getConvertedSunsetTime(): String? {
    this.cityResponse?.sunset?.let {
        return convertPosixFormatToUtcTime(it)
    }
    return null
}

fun TotalWeatherResponse.getDateTime(): String? {
    this.weatherListResponse.map {
        return it.dateTime?.let { posixDateTime ->
            convertPosixFormatToUtcDateTime(posixDateTime)
        }
    }
    return null
}