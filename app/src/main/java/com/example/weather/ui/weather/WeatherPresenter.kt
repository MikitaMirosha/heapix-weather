package com.example.weather.ui.weather

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.weather.WeatherApp
import com.example.weather.base.BaseMvpPresenter
import com.example.weather.net.repo.WeatherRepo
import com.example.weather.net.responses.TotalWeatherResponse
import io.reactivex.Observable
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.kodein.di.instance
import java.math.BigDecimal
import java.math.RoundingMode


@InjectViewState
class WeatherPresenter : BaseMvpPresenter<WeatherView>() {

    private val weatherRepo: WeatherRepo by WeatherApp.kodein.instance()

    private lateinit var totalWeatherResponse: TotalWeatherResponse

    fun onCreate(weatherItemClickObservable: Observable<TotalWeatherResponse>) {
        getWeatherAndUpdateUi()

        setupOnWeatherItemClickListener(weatherItemClickObservable)
    }

    companion object {
        private const val SCALE: Int = 1
        private const val MILLISECONDS: Long = 1000L
        private val ABSOLUTE_ZERO: BigDecimal = BigDecimal(273.15)

        private const val INPUT_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss.sssZ"
        private const val OUTPUT_PATTERN: String = "HH:mm"

        private const val BASE_ICON_URL: String = "http://openweathermap.org/img/w/"
        private const val PNG_FORMAT: String = ".png"
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

    private fun setupWeatherData(totalWeatherResponse: TotalWeatherResponse) {
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
                                "http://openweathermap.org/img/w/${icon}.png"
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
            ?.let { convertPosixToUtc(it) }
            ?.let { viewState.setupSunrise(it) }
    }

    private fun setupSunset(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.city?.sunset
            ?.let { convertPosixToUtc(it) }
            ?.let { viewState.setupSunset(it) }
    }

    private fun convertKelvinToCelsius(kelvinDegrees: BigDecimal): BigDecimal {
        return kelvinDegrees
            .minus(ABSOLUTE_ZERO)
            .setScale(SCALE, RoundingMode.UP)
    }

    private fun convertPosixToUtc(posixTime: Long): String {
        return DateTimeFormat
            .forPattern(OUTPUT_PATTERN)
            .print(
                DateTimeFormat
                    .forPattern(INPUT_PATTERN)
                    .parseDateTime(DateTime(posixTime.times(MILLISECONDS)).toString())
            )
    }

    fun toggleFutureWeatherList() = viewState.toggleFutureWeatherList()

}