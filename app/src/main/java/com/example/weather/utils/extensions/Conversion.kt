package com.example.weather.utils.extensions

import com.example.weather.ui.weather.WeatherPresenter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.math.BigDecimal
import java.math.RoundingMode

fun convertKelvinToCelsius(kelvinDegrees: BigDecimal): BigDecimal {
    return kelvinDegrees
        .minus(WeatherPresenter.ABSOLUTE_ZERO)
        .setScale(WeatherPresenter.SCALE, RoundingMode.UP)
}

fun convertPosixFormatToUtcTime(posixTime: Long): String {
    return DateTimeFormat
        .forPattern(WeatherPresenter.OUTPUT_TIME_PATTERN)
        .print(
            DateTimeFormat
                .forPattern(WeatherPresenter.INPUT_POSIX_PATTERN)
                .parseDateTime(DateTime(posixTime.times(WeatherPresenter.MILLISECONDS)).toString())
        )
}

fun convertPosixFormatToUtcDateTime(posixTime: Long): String {
    return DateTimeFormat
        .forPattern(WeatherPresenter.OUTPUT_DATE_TIME_PATTERN)
        .print(
            DateTimeFormat
                .forPattern(WeatherPresenter.INPUT_POSIX_PATTERN)
                .parseDateTime(DateTime(posixTime.times(WeatherPresenter.MILLISECONDS)).toString())
        )
}