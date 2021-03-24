package com.example.weather.ui.weather

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.weather.MyApp
import com.example.weather.base.BaseMvpPresenter
import com.example.weather.net.repo.WeatherRepo
import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class WeatherPresenter : BaseMvpPresenter<WeatherView>() {

    private val weatherRepo: WeatherRepo by MyApp.kodein.instance()

    fun onCreate(weatherItemClickObservable: Observable<TotalWeatherResponse>) {
        getWeatherAndUpdateUi()

        setupOnWeatherItemClickListener(weatherItemClickObservable)
    }

    private fun getWeatherAndUpdateUi() {
        addDisposable(
            weatherRepo.getTotalWeatherResponse()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {

                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupOnWeatherItemClickListener(weatherItemClickObservable: Observable<TotalWeatherResponse>) {
        addDisposable(
            weatherItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {

                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    fun toggleFutureWeatherList() = viewState.toggleFutureWeatherList()

}