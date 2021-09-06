package ru.fivefourtyfive.wikimapper.data.datasource.remote.util

object Parameters {

    @Synchronized
    fun build(vararg values: String) = buildString {
        with(values.indices) {
            map {
                append(values[it])
                if (it < last) append(",")
            }
        }
    }

    @Synchronized
    fun build(vararg values: Double) = buildString {
        with(values.indices) {
            map {
                append(values[it])
                if (it < last) append(",")
            }
        }
    }
}