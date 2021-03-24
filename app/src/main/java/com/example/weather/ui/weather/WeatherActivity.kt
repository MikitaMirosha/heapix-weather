package com.example.weather.ui.weather

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.weather.R
import com.example.weather.base.BaseMvpActivity
import com.example.weather.ui.weather.adapter.WeatherAdapter
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.view_future_weather_list.*

class WeatherActivity : BaseMvpActivity(), WeatherView {

    @InjectPresenter
    lateinit var weatherPresenter: WeatherPresenter

    private lateinit var weatherAdapter: WeatherAdapter

    override fun getLayoutId(): Int = R.layout.activity_weather

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()

        setupWeatherAdapter()

        weatherPresenter.onCreate(weatherAdapter.weatherItemClickObservable)
    }

    private fun initListeners() {
        vTvShowMore.setOnClickListener {
            weatherPresenter.toggleFutureWeatherList()
        }
    }

    private fun setupWeatherAdapter() {
        weatherAdapter = WeatherAdapter()
        vRvFutureWeatherList.adapter = weatherAdapter
    }

    override fun toggleFutureWeatherList() = vFutureWeatherListBottomSheet.toggle()

}