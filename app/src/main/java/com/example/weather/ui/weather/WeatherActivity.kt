package com.example.weather.ui.weather

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.base.BaseMvpActivity
import com.example.weather.net.responses.WeatherListResponse
import com.example.weather.ui.weather.adapter.WeatherAdapter
import com.github.pwittchen.networkevents.library.NetworkHelper
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.view_future_weather_list.*
import kotlinx.android.synthetic.main.view_weather_description.*
import java.math.BigDecimal

class WeatherActivity : BaseMvpActivity(), WeatherView {

    @InjectPresenter
    lateinit var weatherPresenter: WeatherPresenter

    private lateinit var weatherAdapter: WeatherAdapter

    override fun getLayoutId(): Int = R.layout.activity_weather

    override fun onCreateActivity(savedInstanceState: Bundle?) {

        initListeners()
        setupWeatherAdapter()

        weatherPresenter.onCreate(NetworkHelper.isConnectedToWiFiOrMobileNetwork(this))
    }

    private fun initListeners() {

        vTvShowMore.setOnClickListener {
            weatherPresenter.onShowMoreButtonClicked()
        }

        vSwipeRefreshLayout.setOnRefreshListener {
            weatherPresenter.onLayoutRefreshed()
            vSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupWeatherAdapter() {
        weatherAdapter = WeatherAdapter()
        vRvFutureWeatherList.adapter = weatherAdapter
    }

    override fun setupWeatherItems(weatherListResponse: MutableList<WeatherListResponse>) {
        weatherAdapter.setItems(weatherListResponse)
    }

    override fun setupCityName(cityName: String) {
        vTvCityName.text = cityName
    }

    override fun setupWeatherConditionIcon(weatherConditionIcon: String) {
        Glide.with(applicationContext)
            .load(weatherConditionIcon)
            .error(android.R.drawable.stat_notify_error)
            .into(vIvWeatherConditionIcon)
    }

    override fun setupTemperature(temperature: BigDecimal) {
        vTvTemperature.text = temperature.toPlainString()
    }

    override fun setupWeatherDescription(weatherDescription: String) {
        vTvWeatherDescription.text = weatherDescription
    }

    override fun setupTemperatureFeelsLike(temperatureFeelsLike: BigDecimal) {
        vTvTemperatureFeelsLike.text = temperatureFeelsLike.toPlainString()
    }

    override fun setupPressure(pressure: Int) {
        vTvPressure.text = pressure.toString()
    }

    override fun setupHumidity(humidity: Int) {
        vTvHumidity.text = humidity.toString()
    }

    override fun setupWindSpeed(windSpeed: BigDecimal) {
        vTvWindSpeed.text = windSpeed.toPlainString()
    }

    override fun setupSunrise(sunrise: String) {
        vTvSunrise.text = sunrise
    }

    override fun setupSunset(sunset: String) {
        vTvSunset.text = sunset
    }

    override fun setupDateTime(dateTime: String) {
        vTvDateTime.text = dateTime
    }

    override fun toggleFutureWeatherList() {
        vFutureWeatherListBottomSheet.toggle()
    }
}