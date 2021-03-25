package com.example.weather.utils.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.weather.net.responses.TotalWeatherResponse
import com.example.weather.utils.preferences.PreferencesUtils.Companion.getSharedPreferences
import com.google.gson.Gson


class PreferencesUtils {

    companion object {
        fun getSharedPreferences(context: Context) =
            context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)!!

        private const val PREFERENCES = "PREFERENCES"


        const val DESCRIPTION = "DESCRIPTION"
        const val PRESSURE = "PRESSURE"

        const val TOTAL_WEATHER_RESPONSE = "TOTAL_WEATHER_RESPONSE"
    }
}

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

//var SharedPreferences.totalWeatherResponse: TotalWeatherResponse
//    get() = getString(PreferencesUtils.TOTAL_WEATHER_RESPONSE, "") ?: ""
//    set(value) = editMe { it.putString(PreferencesUtils.TOTAL_WEATHER_RESPONSE, value) }

var SharedPreferences.description: String
    get() = getString(PreferencesUtils.DESCRIPTION, "") ?: ""
    set(value) = editMe { it.putString(PreferencesUtils.DESCRIPTION, value) }

var SharedPreferences.pressure: Int
    get() = getInt(PreferencesUtils.PRESSURE, 0)
    set(value) = editMe { it.putInt(PreferencesUtils.PRESSURE, value) }



