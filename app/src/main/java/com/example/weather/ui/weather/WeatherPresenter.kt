package com.example.weather.ui.weather

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.weather.WeatherApp
import com.example.weather.base.BaseMvpPresenter
import com.example.weather.net.repo.WeatherRepo
import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.net.responses.WeatherListResponse
import com.example.weather.utils.extensions.convertKelvinToCelsius
import com.example.weather.utils.extensions.convertPosixFormatToUtcTime
import io.reactivex.Observable
import org.kodein.di.instance
import java.math.BigDecimal

@InjectViewState
class WeatherPresenter : BaseMvpPresenter<WeatherView>() {

    private val weatherRepo: WeatherRepo by WeatherApp.kodein.instance()

    private lateinit var totalWeatherResponse: TotalWeatherResponse

    fun onCreate(weatherItemClickObservable: Observable<WeatherListResponse>) {
        getWeatherAndUpdateUi()

        setupOnWeatherItemClickListener(weatherItemClickObservable)
    }

    companion object {
        const val SCALE: Int = 1
        const val MILLISECONDS: Long = 1000L
        val ABSOLUTE_ZERO: BigDecimal = BigDecimal(273.15)

        const val OUTPUT_TIME_PATTERN: String = "HH:mm"
        const val OUTPUT_DATE_TIME_PATTERN: String = "HH:mm  dd.MM"
        const val INPUT_POSIX_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss.sssZ"

        const val PNG_FORMAT: String = ".png"
        const val BASE_ICON_URL: String = "https://openweathermap.org/img/w/"
    }

    private fun getWeatherAndUpdateUi() {
        addDisposable(
            weatherRepo.getTotalWeatherResponse()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        totalWeatherResponse = it

                        setupWeatherData(totalWeatherResponse)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupOnWeatherItemClickListener(weatherItemClickObservable: Observable<WeatherListResponse>) {
        addDisposable(
            weatherItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        getWeatherAndUpdateUi()
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupWeatherData(totalWeatherResponse: TotalWeatherResponse) {
        setupWeatherItems(totalWeatherResponse)

        setupCityName(totalWeatherResponse)
        setupWeatherConditionIcon(totalWeatherResponse)
        setupTemperature(totalWeatherResponse)
        setupWeatherDescription(totalWeatherResponse)

        setupTemperatureFeelsLike()
        setupPressure()
        setupHumidity()
        setupWindSpeed()
        setupSunrise(totalWeatherResponse)
        setupSunset(totalWeatherResponse)
        setupDateTime(totalWeatherResponse)
    }

    private fun setupWeatherItems(totalWeatherResponse: TotalWeatherResponse) {
        viewState.setupWeatherItems(totalWeatherResponse.list)
    }

    private fun setupCityName(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.city?.name
            ?.let { name -> viewState.setupCityName(name) }
    }

    private fun setupWeatherConditionIcon(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.list.map { weatherListResponse ->
            weatherListResponse.weather.map { weatherResponse ->
                weatherResponse.icon
            }.let { iconList ->
                iconList.map { icon ->
                    icon.let {
                        it?.let { icon ->
                            viewState.setupWeatherConditionIcon(
                                BASE_ICON_URL
                                    .plus(icon)
                                    .plus(PNG_FORMAT)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setupTemperature(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.list.map { weatherListResponse ->
            weatherListResponse.main?.temp
        }.let { temperatureList ->
            temperatureList.map { temperature ->
                temperature?.let { kelvinDegrees ->
                    convertKelvinToCelsius(kelvinDegrees).let {
                        viewState.setupTemperature(it)
                    }
                }
            }
        }
    }

    private fun setupWeatherDescription(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.list.map { weatherListResponse ->
            weatherListResponse.weather.map { weatherResponse ->
                weatherResponse.description
            }.let { descriptionList ->
                descriptionList.map { description ->
                    description?.let { viewState.setupWeatherDescription(it) }
                }
            }
        }
    }

    private fun setupTemperatureFeelsLike() {
        totalWeatherResponse.list.map { weatherListResponse ->
            weatherListResponse.main?.feelsLike
        }.let { temperatureFeelsLikeList ->
            temperatureFeelsLikeList.map { temperatureFeelsLike ->
                temperatureFeelsLike?.let { kelvinDegrees ->
                    convertKelvinToCelsius(kelvinDegrees).let {
                        viewState.setupTemperatureFeelsLike(it)
                    }
                }
            }
        }
    }

    private fun setupPressure() {
        totalWeatherResponse.list.map { weatherListResponse ->
            weatherListResponse.main?.pressure
        }.let { pressureList ->
            pressureList.map { pressure ->
                pressure?.let {
                    viewState.setupPressure(it)
                }
            }
        }
    }

    private fun setupHumidity() {
        totalWeatherResponse.list.map { weatherListResponse ->
            weatherListResponse.main?.humidity
        }.let { humidityList ->
            humidityList.map { humidity ->
                humidity?.let {
                    viewState.setupHumidity(it)
                }
            }
        }
    }

    private fun setupWindSpeed() {
        totalWeatherResponse.list.map { weatherListResponse ->
            weatherListResponse.wind?.speed
        }.let { windList ->
            windList.map { wind ->
                wind?.let {
                    viewState.setupWindSpeed(it)
                }
            }
        }
    }

    private fun setupSunrise(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.city?.sunrise
            ?.let { convertPosixFormatToUtcTime(it) }
            ?.let { viewState.setupSunrise(it) }
    }

    private fun setupSunset(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.city?.sunset
            ?.let { convertPosixFormatToUtcTime(it) }
            ?.let { viewState.setupSunset(it) }
    }

    private fun setupDateTime(totalWeatherResponse: TotalWeatherResponse) {
//        totalWeatherResponse.list.map { weatherListResponse ->
//            weatherListResponse.dt
//                ?.let { convertPosixFormatToUtcDateTime(it) }
//                ?.let { viewState.setupDateTime(it.toLong()) }
//        }
    }

    fun toggleFutureWeatherList() = viewState.toggleFutureWeatherList()
}