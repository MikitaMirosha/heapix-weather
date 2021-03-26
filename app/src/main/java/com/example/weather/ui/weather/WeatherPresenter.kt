package com.example.weather.ui.weather

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.weather.WeatherApp
import com.example.weather.base.BaseMvpPresenter
import com.example.weather.net.repo.WeatherRepo
import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.net.responses.WeatherListResponse
import com.example.weather.utils.extensions.convertKelvinToCelsius
import com.example.weather.utils.extensions.convertPosixFormatToUtcTime
import com.google.gson.Gson
import io.reactivex.Observable
import org.kodein.di.instance
import java.math.BigDecimal


@InjectViewState
class WeatherPresenter : BaseMvpPresenter<WeatherView>() {

    private val weatherRepo: WeatherRepo by WeatherApp.kodein.instance()

    private lateinit var runnable: Runnable

    private lateinit var totalWeatherResponse: TotalWeatherResponse

    var mPrefs = preferences

    fun onCreate(
        weatherItemClickObservable: Observable<WeatherListResponse>,
        isNetworkAvailable: Boolean
    ) {

        when (isNetworkAvailable) {
            true -> getWeatherAndUpdateUi()
            false -> getWeatherDataFromSharedPreferences()
        }

        setupOnWeatherItemClickListener(weatherItemClickObservable)
    }

    companion object {
        const val SCALE: Int = 1
        const val DELAY_MILLIS: Long = 3000L
        const val MILLISECONDS: Long = 1000L
        val ABSOLUTE_ZERO: BigDecimal = BigDecimal(273.15)

        const val CITY_NAME: String = "Minsk"

        const val OUTPUT_TIME_PATTERN: String = "HH:mm"
        const val OUTPUT_DATE_TIME_PATTERN: String = "HH:mm  dd.MM"
        const val INPUT_POSIX_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss.sssZ"

        const val PNG_FORMAT: String = ".png"
        const val BASE_ICON_URL: String = "https://openweathermap.org/img/w/"
    }

    private fun getWeatherAndUpdateUi() {
        addDisposable(
            weatherRepo.getTotalWeatherResponse(CITY_NAME)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        totalWeatherResponse = it

                        setupWeatherData(totalWeatherResponse)

                        saveTotalWeatherResponseToSharedPreferences(totalWeatherResponse)

                        saveWeatherData(totalWeatherResponse)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getWeatherDataFromSharedPreferences() {
        val totalWeatherResponse = getTotalWeatherResponseFromSharedPreferences()

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

        setupTemperatureFeelsLike(totalWeatherResponse)
        setupPressure(totalWeatherResponse)
        setupHumidity(totalWeatherResponse)
        setupWindSpeed(totalWeatherResponse)
        setupSunrise(totalWeatherResponse)
        setupSunset(totalWeatherResponse)
        setupDateTime(totalWeatherResponse)
    }

    private fun saveTotalWeatherResponseToSharedPreferences(totalWeatherResponse: TotalWeatherResponse) {
        val prefsEditor: SharedPreferences.Editor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(totalWeatherResponse)
        prefsEditor.putString("totalWeatherResponse", json)
        prefsEditor.apply()
    }

    private fun getTotalWeatherResponseFromSharedPreferences(): TotalWeatherResponse {
        val gson = Gson()
        val json = mPrefs.getString("totalWeatherResponse", "")
        return gson.fromJson(json, TotalWeatherResponse::class.java)
    }

    private fun saveWeatherData(totalWeatherResponse: TotalWeatherResponse) {

    }

    private fun setupWeatherItems(totalWeatherResponse: TotalWeatherResponse) {
        viewState.setupWeatherItems(totalWeatherResponse.weatherListResponse)
    }

    private fun setupCityName(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.cityResponse?.name
            ?.let { name -> viewState.setupCityName(name) }
    }

    private fun setupWeatherConditionIcon(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.weatherListResponse.map { weatherListResponse ->
            weatherListResponse.weatherResponses.map { weatherResponse ->
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
        totalWeatherResponse.weatherListResponse.map { weatherListResponse ->
            weatherListResponse.mainParametersResponse?.temperature
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
        totalWeatherResponse.weatherListResponse.map { weatherListResponse ->
            weatherListResponse.weatherResponses.map { weatherResponse ->
                weatherResponse.description
            }.let { descriptionList ->
                descriptionList.map { description ->
                    description?.let { viewState.setupWeatherDescription(it) }
                }
            }
        }
    }

    private fun setupTemperatureFeelsLike(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.weatherListResponse.map { weatherListResponse ->
            weatherListResponse.mainParametersResponse?.feelsLike
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

    private fun setupPressure(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.weatherListResponse.map { weatherListResponse ->
            weatherListResponse.mainParametersResponse?.pressure
        }.let { pressureList ->
            pressureList.map { pressure ->
                pressure?.let {
                    viewState.setupPressure(it)
                }
            }
        }
    }

    private fun setupHumidity(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.weatherListResponse.map { weatherListResponse ->
            weatherListResponse.mainParametersResponse?.humidity
        }.let { humidityList ->
            humidityList.map { humidity ->
                humidity?.let {
                    viewState.setupHumidity(it)
                }
            }
        }
    }

    private fun setupWindSpeed(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.weatherListResponse.map { weatherListResponse ->
            weatherListResponse.windResponse?.speed
        }.let { windList ->
            windList.map { wind ->
                wind?.let {
                    viewState.setupWindSpeed(it)
                }
            }
        }
    }

    private fun setupSunrise(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.cityResponse?.sunrise
            ?.let { convertPosixFormatToUtcTime(it) }
            ?.let { viewState.setupSunrise(it) }
    }

    private fun setupSunset(totalWeatherResponse: TotalWeatherResponse) {
        totalWeatherResponse.cityResponse?.sunset
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

    fun onShowMoreButtonClicked() = viewState.toggleFutureWeatherList()

    fun onLayoutRefreshed() {
        runnable = Runnable {
            getWeatherAndUpdateUi()
        }

        Handler(Looper.getMainLooper())
            .postDelayed(runnable, DELAY_MILLIS)
    }
}