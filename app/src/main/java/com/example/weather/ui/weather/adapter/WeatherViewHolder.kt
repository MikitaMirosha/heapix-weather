package com.example.weather.ui.weather.adapter

import android.view.View
import com.example.weather.base.adapters.BaseViewHolder
import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_future_weather.*

class WeatherViewHolder(
    itemView: View,
    private val totalWeatherPublishSubject: PublishSubject<TotalWeatherResponse>
) : BaseViewHolder<TotalWeatherResponse>(itemView) {

    override fun bind(model: TotalWeatherResponse) {
        setupItemTemperature(model)

        setupClickListener(model)
    }

    private fun setupItemTemperature(totalWeatherResponse: TotalWeatherResponse) {
        //vTvItemTemperature.text = totalWeatherResponse.list
    }

    private fun setupClickListener(totalWeatherResponse: TotalWeatherResponse) {
        setOnClickListener {
            totalWeatherPublishSubject.onNext(totalWeatherResponse)
        }
    }

}