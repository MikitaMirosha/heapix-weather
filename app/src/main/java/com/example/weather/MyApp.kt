package com.example.weather

import android.app.Application
import android.content.SharedPreferences
import com.example.weather.net.repo.WeatherRepo
import com.example.weather.net.services.ApiRest
import com.example.weather.net.services.WeatherService
import com.heapix.exchange.utils.preferences.PreferencesUtils
import com.heapix.exchange.utils.rx.AppSchedulerProvider
import com.heapix.exchange.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.*
import retrofit2.Retrofit

private lateinit var kodeinStored: DI

class MyApp : Application() {

    private val settingModule = DI.Module("settings Module") {

        bind<Retrofit>() with singleton { ApiRest.getApi() }

        bind<CompositeDisposable>() with provider { CompositeDisposable() }

        bind<SchedulerProvider>() with singleton { AppSchedulerProvider() }

        bind<SharedPreferences>() with singleton {
            PreferencesUtils.getSharedPreferences(applicationContext)
        }

        bind<WeatherRepo>() with singleton {
            WeatherRepo(
                instance<Retrofit>().create(WeatherService::class.java),
                instance()
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
