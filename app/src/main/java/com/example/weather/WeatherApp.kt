package com.example.weather

import android.app.Application
import android.content.SharedPreferences
import com.example.weather.net.repo.WeatherRepo
import com.example.weather.net.services.ApiRest
import com.example.weather.net.services.WeatherService
import com.example.weather.utils.rx.AppSchedulerProvider
import com.example.weather.utils.rx.SchedulerProvider
import com.example.weather.utils.preferences.PreferencesUtils
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.*
import retrofit2.Retrofit

private lateinit var kodeinStored: DI

class WeatherApp : Application() {

    private val settingModule = DI.Module("settingModule") {

        bind<Retrofit>() with singleton { ApiRest.getApi() }

        bind<CompositeDisposable>() with provider { CompositeDisposable() }

        bind<SchedulerProvider>() with singleton { AppSchedulerProvider() }

        bind<SharedPreferences>() with singleton {
            PreferencesUtils.getSharedPreferences(applicationContext)
        }

        bind<WeatherRepo>() with provider {
            WeatherRepo(
                instance<Retrofit>().create(WeatherService::class.java)
            )
        }
    }

    companion object {
        var kodein: DI
            get() = kodeinStored
            set(_) {}

        fun isKodeinInitialized() = ::kodeinStored.isInitialized
    }

    override fun onCreate() {
        super.onCreate()

        if (::kodeinStored.isInitialized.not())
            kodeinStored = DI {
                import(settingModule)
            }
    }
}