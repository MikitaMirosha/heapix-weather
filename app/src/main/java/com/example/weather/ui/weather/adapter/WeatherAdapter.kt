package com.example.weather.ui.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.base.adapters.BaseListAdapter
import com.example.weather.base.adapters.BaseViewHolder
import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class WeatherAdapter : BaseListAdapter<TotalWeatherResponse>() {

    private val totalWeatherPublishSubject: PublishSubject<TotalWeatherResponse> =
        PublishSubject.create()
    val weatherItemClickObservable: Observable<TotalWeatherResponse> = totalWeatherPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TotalWeatherResponse> {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_future_weather, parent, false),
            totalWeatherPublishSubject
        )
    }

}
