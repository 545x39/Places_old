package ru.fivefourtyfive.wikimapper.data.datasource.abstraction

import ru.fivefourtyfive.wikimapper.domain.entity.Places
import ru.fivefourtyfive.wikimapper.domain.entity.Place

interface LocalDataSource {

    fun persistPlace(place: Place): Boolean

    fun getPlace(id: Int): Place

    fun getArea(latMin: Double,
                lonMin: Double,
                latMax: Double,
                lonMax: Double,
                category: String?,
                count: Int?,
                language: String?): Places
}