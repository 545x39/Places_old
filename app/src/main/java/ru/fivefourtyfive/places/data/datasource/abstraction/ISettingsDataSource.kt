package ru.fivefourtyfive.places.data.datasource.abstraction

interface ISettingsDataSource {

    //<editor-fold defaultstate="collapsed" desc="GETTERS">
    fun getString(key: String, default: String = ""): String?

    fun getInt(key: String, default: Int = -1): Int

    fun getFloat(key: String, default: Float = .0f): Float

    fun getDouble(key: String, default: Double = .0): Double

    fun getBoolean(key: String, default: Boolean = false): Boolean

    fun getPairOfDouble(key: String, default: String): Pair<Double, Double>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETTERS">
    fun putString(key: String, value: String)

    fun putInt(key: String, value: Int)

    fun putFloat(key: String, value: Float)

    fun putDouble(key: String, value: Double)

    fun putBoolean(key: String, value: Boolean)

    fun putPairOfDouble(key: String, pair: Pair<Double, Double>)
    //</editor-fold>

}