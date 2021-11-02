package ru.fivefourtyfive.wikimapper.util

import android.content.SharedPreferences

@Suppress("SameParameterValue")
abstract class SettingsUtil constructor(protected val preferences: SharedPreferences) {

    protected fun putString(key: String, value: String) = preferences.edit().putString(key, value).apply()

    protected fun putInt(key: String, value: Int) = preferences.edit().putInt(key, value).apply()

    protected fun putDouble(key: String, value: Double) = preferences.edit().putFloat(key, value.toFloat()).apply()

    protected fun putBoolean(key: String, value: Boolean) = preferences.edit().putBoolean(key, value).apply()

    protected fun getPairOfDouble(key: String, default: String): Pair<Double, Double> {
        preferences.getString(key, default)!!.split(";").apply {
            return this[0].toDouble() to this[1].toDouble()
        }
    }
}