package ru.fivefourtyfive.wikimapper.data.datasource.remote.util

import ru.fivefourtyfive.wikimapper.util.ifTrue

object Parameters {

    @Synchronized
    fun add(vararg values: String) = buildString {
        with(values.indices) {
            map {
                append(values[it])
                (it < last).ifTrue { append(",") }
            }
        }
    }

    @Synchronized
    fun add(vararg values: Double) = buildString {
        with(values.indices) {
            map {
                append(values[it])
                (it < last).ifTrue { append(",") }
            }
        }
    }
}