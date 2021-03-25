package com.example.weather.ui.weather.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.example.weather.base.adapters.BaseViewHolder
import com.example.weather.net.responses.WeatherListResponse
import com.example.weather.ui.weather.WeatherPresenter.Companion.BASE_ICON_URL
import com.example.weather.ui.weather.WeatherPresenter.Companion.PNG_FORMAT
import com.example.weather.utils.extensions.convertKelvinToCelsius
import com.example.weather.utils.extensions.convertPosixFormatToUtcDateTime
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_future_weather.*

class WeatherViewHolder(
    itemView: View,
    private val totalWeatherPublishSubject: PublishSubject<WeatherListResponse>
) : BaseViewHolder<WeatherListResponse>(itemView) {

    override fun bind(model: WeatherListResponse) {
        setupItemWeatherConditionIcon(model)
        setupItemTemperature(model)
        setupItemDateTime(model)
        setupItemWeatherDescription(model)
        setupItemTemperatureFeelsLike(model)
        setupItemWindSpeed(model)

        setupClickListener(model)
    }

    private fun setupItemWeatherConditionIcon(weatherListResponse: WeatherListResponse) {
        weatherListResponse.weather.map { weatherResponse ->
            weatherResponse.icon
        }.let { iconList ->
            iconList.map { icon ->
                icon?.let {
                    Glide.with(context)
                        .load(BASE_ICON_URL + icon + PNG_FORMAT)
                        .error(android.R.drawable.stat_notify_error)
                        .into(vIvItemWeatherConditionIcon)
                }
            }
        }
    }

    private fun setupItemTemperature(weatherListResponse: WeatherListResponse) {
        vTvItemTemperature.text = weatherListResponse.main?.temp?.let { kelvinDegrees ->
            convertKelvinToCelsius(kelvinDegrees).toPlainString()
        }
    }

    private fun setupItemDateTime(weatherListResponse: WeatherListResponse) {
        vTvItemDateTime.text = weatherListResponse.dt.let { dateTime ->
            dateTime?.let { posixDateTime ->
                convertPosixFormatToUtcDateTime(posixDateTime)
            }
        }
    }

    private fun setupItemWeatherDescription(weatherListResponse: WeatherListResponse) {
        weatherListResponse.weather.map { weatherResponse ->
            weatherResponse.description
        }.let { descriptionList ->
            descriptionList.map { description ->
                description?.let {
                    vTvItemWeatherDescription.text = it
                }
            }
        }
    }

    private fun setupItemTemperatureFeelsLike(weatherListResponse: WeatherListResponse) {
        vTvItemTemperatureFeelsLike.text =
            weatherListResponse.main?.feelsLike.let { temperatureFeelsLike ->
                temperatureFeelsLike?.let { kelvinDegrees ->
                    convertKelvinToCelsius(kelvinDegrees).toPlainString()
                }
            }
    }

    private fun setupItemWindSpeed(weatherListResponse: WeatherListResponse) {
        vTvItemWindSpeed.text = weatherListResponse.wind?.speed.toString()
    }

    private fun setupClickListener(weatherListResponse: WeatherListResponse) {
        setOnClickListener {
            totalWeatherPublishSubject.onNext(weatherListResponse)
        }
    }

}