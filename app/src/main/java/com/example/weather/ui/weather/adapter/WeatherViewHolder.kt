package com.example.weather.ui.weather.adapter

import android.view.View
import com.example.weather.base.adapters.BaseViewHolder
import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.subjects.PublishSubject
import java.math.RoundingMode

class WeatherViewHolder(
    itemView: View,
    private val totalWeatherPublishSubject: PublishSubject<TotalWeatherResponse>
) : BaseViewHolder<TotalWeatherResponse>(itemView) {

    override fun bind(model: TotalWeatherResponse) {
        setupBaseRate(model)

        setupClickListener(model)
    }

    private fun setupBaseRate(totalWeatherResponse: TotalWeatherResponse) {

    }

    private fun setupClickListener(totalWeatherResponse: TotalWeatherResponse) {
        setOnClickListener {
            totalWeatherPublishSubject.onNext(totalWeatherResponse)
        }
    }

}