package ru.fivefourtyfive.places.framework.datasource.settings

import android.content.SharedPreferences
import ru.fivefourtyfive.places.data.datasource.abstraction.ISettingsDataSource
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(private val preferences: SharedPreferences) :
    ISettingsDataSource {

    override fun getString(key: String, default: String) = preferences.getString(key, default)

    override fun getInt(key: String, default: Int) = preferences.getInt(key, default)

    override fun getFloat(key: String, default: Float) = preferences.getFloat(key,default)

    override fun getDouble(key: String, default: Double) = preferences.getFloat(key, default.toFloat()).toDouble()

    override fun getBoolean(key: String, default: Boolean) = preferences.getBoolean(key, default)

    override fun putString(key: String, value: String) =
        preferences.edit().putString(key, value).apply()

    override fun putInt(key: String, value: Int) = preferences.edit().putInt(key, value).apply()

    override fun putFloat(key: String, value: Float)  = preferences.edit().putFloat(key, value).apply()

    override fun putDouble(key: String, value: Double) =
        preferences.edit().putFloat(key, value.toFloat()).apply()

    override fun putBoolean(key: String, value: Boolean) =
        preferences.edit().putBoolean(key, value).apply()

    override fun getPairOfDouble(key: String, default: String): Pair<Double, Double> {
        preferences.getString(key, default)!!.split(";").apply {
            return this[0].toDouble() to this[1].toDouble()
        }
    }

    override fun putPairOfDouble(key: String, pair: Pair<Double, Double>) = putString(key, "${pair.first};${pair.second}")
}