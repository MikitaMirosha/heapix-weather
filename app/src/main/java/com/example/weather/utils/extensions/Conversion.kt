package com.example.weather.utils.extensions

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.math.BigDecimal
import java.math.RoundingMode

const val SCALE: Int = 1
const val MILLISECONDS: Long = 1000L
val ABSOLUTE_ZERO: BigDecimal = BigDecimal(273.15)

const val OUTPUT_TIME_PATTERN: String = "HH:mm"
const val OUTPUT_DATE_TIME_PATTERN: String = "HH:mm  dd.MM"
const val INPUT_POSIX_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss.sssZ"

fun convertKelvinToCelsius(kelvinDegrees: BigDecimal): BigDecimal {
    return kelvinDegrees
        .minus(ABSOLUTE_ZERO)
        .setScale(SCALE, RoundingMode.UP)
}

fun convertPosixFormatToUtcTime(posixTime: Long): String {
    return DateTimeFormat
        .forPattern(OUTPUT_TIME_PATTERN)
        .print(
            DateTimeFormat
                .forPattern(INPUT_POSIX_PATTERN)
                .parseDateTime(DateTime(posixTime.times(MILLISECONDS)).toString())
        )
}

fun convertPosixFormatToUtcDateTime(posixTime: Long): String {
    return DateTimeFormat
        .forPattern(OUTPUT_DATE_TIME_PATTERN)
        .print(
            DateTimeFormat
                .forPattern(INPUT_POSIX_PATTERN)
                .parseDateTime(DateTime(posixTime.times(MILLISECONDS)).toString())
        )
}