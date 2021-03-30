package com.example.weather.ui.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.base.adapters.BaseListAdapter
import com.example.weather.base.adapters.BaseViewHolder
import com.example.weather.net.responses.WeatherListResponse

class WeatherAdapter : BaseListAdapter<WeatherListResponse>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<WeatherListResponse> {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_future_weather, parent, false)
        )
    }
}
