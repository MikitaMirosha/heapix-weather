package com.example.weather.ui.weather

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.weather.WeatherApp
import com.example.weather.base.BaseMvpPresenter
import com.example.weather.net.repo.WeatherRepo
import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.utils.extensions.*
import com.google.gson.Gson
import org.kodein.di.instance

@InjectViewState
class WeatherPresenter : BaseMvpPresenter<WeatherView>() {

    private val weatherRepo: WeatherRepo by WeatherApp.kodein.instance()

    private lateinit var runnable: Runnable

    fun onCreate(isNetworkAvailable: Boolean) {

        when (isNetworkAvailable) {
            true -> getWeatherAndUpdateUi()
            false -> getWeatherFromStorage()
        }
    }

    companion object {
        const val DELAY_MILLIS: Long = 3000L

        const val CITY_NAME: String = "Minsk"
        const val PNG_FORMAT: String = ".png"
        const val TOTAL_WEATHER_RESPONSE: String = "totalWeatherResponse"
        const val BASE_ICON_URL: String = "https://openweathermap.org/img/w/"
    }

    private fun getWeatherAndUpdateUi() {
        addDisposable(
            weatherRepo.getTotalWeatherResponse(CITY_NAME)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        setupWeather(it)

                        saveWeather(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getWeatherFromStorage() {
        val totalWeatherResponse = getWeather()

        setupWeatherItems(totalWeatherResponse)

        setupCityName(totalWeatherResponse)
        setupWeatherConditionIcon(totalWeatherResponse)
        setupTemperature(totalWeatherResponse)
        setupWeatherDescription(totalWeatherResponse)

        setupTemperatureFeelsLike(totalWeatherResponse)
        setupPressure(totalWeatherResponse)
        setupHumidity(totalWeatherResponse)
        setupWindSpeed(totalWeatherResponse)
        setupSunrise(totalWeatherResponse)
        setupSunset(totalWeatherResponse)
        setupDateTime(totalWeatherResponse)
    }

    private fun setupWeather(totalWeatherResponse: TotalWeatherResponse) {
        setupWeatherItems(totalWeatherResponse)

        setupCityName(totalWeatherResponse)
        setupWeatherConditionIcon(totalWeatherResponse)
        setupTemperature(totalWeatherResponse)
        setupWeatherDescription(totalWeatherResponse)

        setupTemperatureFeelsLike(totalWeatherResponse)
        setupPressure(totalWeatherResponse)
        setupHumidity(totalWeatherResponse)
        setupWindSpeed(totalWeatherResponse)
        setupSunrise(totalWeatherResponse)
        setupSunset(totalWeatherResponse)
        setupDateTime(totalWeatherResponse)
    }

    private fun saveWeather(totalWeatherResponse: TotalWeatherResponse) {
        val prefsEditor = preferences.edit()

        prefsEditor.putString(TOTAL_WEATHER_RESPONSE, Gson().toJson(totalWeatherResponse))
        prefsEditor.apply()
    }

    private fun getWeather(): TotalWeatherResponse {
        val json = preferences.getString(TOTAL_WEATHER_RESPONSE, "")
        return Gson().fromJson(json, TotalWeatherResponse::class.java)
    }

    private fun setupWeatherItems(totalWeatherResponse: TotalWeatherResponse) {
        viewState.setupWeatherItems(totalWeatherResponse.weatherListResponse)
    }

    private fun setupCityName(totalWeatherResponse: TotalWeatherResponse) {
        viewState.setupCityName(totalWeatherResponse.getCity())
    }

    private fun setupWeatherConditionIcon(totalWeatherResponse: TotalWeatherResponse) {
        viewState.setupWeatherConditionIcon(
            BASE_ICON_URL
                .plus(totalWeatherResponse.getWeatherConditionIcon())
                .plus(PNG_FORMAT)
        )
    }

    private fun setupTemperature(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getConvertedTemperature()?.let {
            viewState.setupTemperature(it)
        }
    }

    private fun setupWeatherDescription(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getWeatherDescription()?.let {
            viewState.setupWeatherDescription(it)
        }
    }

    private fun setupTemperatureFeelsLike(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getConvertedTemperatureFeelsLike()?.let {
            viewState.setupTemperatureFeelsLike(it)
        }
    }

    private fun setupPressure(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getPressure()?.let {
            viewState.setupPressure(it)
        }
    }

    private fun setupHumidity(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getHumidity()?.let {
            viewState.setupHumidity(it)
        }
    }

    private fun setupWindSpeed(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getWindSpeed()?.let {
            viewState.setupWindSpeed(it)
        }
    }

    private fun setupSunrise(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getConvertedSunriseTime()?.let {
            viewState.setupSunrise(it)
        }
    }

    private fun setupSunset(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getConvertedSunsetTime()?.let {
            viewState.setupSunset(it)
        }
    }

    private fun setupDateTime(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.getDateTime()?.let {
            viewState.setupDateTime(it)
        }
    }

    fun onShowMoreButtonClicked() {
        viewState.toggleFutureWeatherList()
    }

    fun onLayoutRefreshed() {
        runnable = Runnable {
            getWeatherAndUpdateUi()
        }

        Handler(Looper.getMainLooper())
            .postDelayed(runnable, DELAY_MILLIS)
    }
}