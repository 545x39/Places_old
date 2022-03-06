package ru.fivefourtyfive.places.framework.datasource.implementation.local.database.typeconverter

import androidx.room.TypeConverter
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.typeconverter.util.CoordinateDelimiters.VALUE_DELIMITER
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.typeconverter.util.CoordinateDelimiters.COORDINATE_DELIMITER
import ru.fivefourtyfive.places.domain.entity.places.PolygonPoint
import ru.fivefourtyfive.places.util.ifTrue

class PolygonConverter {

    @TypeConverter
    fun polygonToString(polygon: List<PolygonPoint>) = buildString {
        for (index in polygon.indices){
            append("${polygon[index].x}$VALUE_DELIMITER${polygon[index].y}")
            (index < polygon.size-1).ifTrue {append(COORDINATE_DELIMITER)}
        }
    }

    @TypeConverter
    fun stringToPolygon(polygon: String) = arrayListOf<PolygonPoint>().apply {
        polygon.split(COORDINATE_DELIMITER).map {
            it.split(VALUE_DELIMITER).apply {
                add(PolygonPoint(this[0].toDouble(), this[1].toDouble()))
            }
        }
    }.toList()
}