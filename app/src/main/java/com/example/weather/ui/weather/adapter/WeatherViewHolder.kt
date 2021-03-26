package com.example.weather.ui.weather.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.example.weather.base.adapters.BaseViewHolder
import com.example.weather.net.responses.WeatherListResponse
import com.example.weather.ui.weather.WeatherPresenter.Companion.BASE_ICON_URL
import com.example.weather.ui.weather.WeatherPresenter.Companion.PNG_FORMAT
import com.example.weather.utils.extensions.*
import kotlinx.android.synthetic.main.item_future_weather.*

class WeatherViewHolder(itemView: View) : BaseViewHolder<WeatherListResponse>(itemView) {

    override fun bind(model: WeatherListResponse) {
        setupItemWeatherConditionIcon(model)
        setupItemTemperature(model)
        setupItemDateTime(model)
        setupItemWeatherDescription(model)
        setupItemTemperatureFeelsLike(model)
        setupItemWindSpeed(model)
    }

    private fun setupItemWeatherConditionIcon(weatherListResponse: WeatherListResponse) {
        Glide.with(context)
            .load(
                BASE_ICON_URL
                    .plus(weatherListResponse.getWeatherConditionIcon())
                    .plus(PNG_FORMAT)
            )
            .error(android.R.drawable.stat_notify_error)
            .into(vIvItemWeatherConditionIcon)
    }

    private fun setupItemTemperature(weatherListResponse: WeatherListResponse) {
        vTvItemTemperature.text = weatherListResponse.getConvertedTemperature()
    }

    private fun setupItemDateTime(weatherListResponse: WeatherListResponse) {
        vTvItemDateTime.text = weatherListResponse.getConvertedDateTime()
    }

    private fun setupItemWeatherDescription(weatherListResponse: WeatherListResponse) {
        vTvItemWeatherDescription.text = weatherListResponse.getWeatherDescription()
    }

    private fun setupItemTemperatureFeelsLike(weatherListResponse: WeatherListResponse) {
        vTvItemTemperatureFeelsLike.text = weatherListResponse.getConvertedTemperatureFeelsLike()
    }

    private fun setupItemWindSpeed(weatherListResponse: WeatherListResponse) {
        vTvItemWindSpeed.text = weatherListResponse.getWindSpeed()
    }
}