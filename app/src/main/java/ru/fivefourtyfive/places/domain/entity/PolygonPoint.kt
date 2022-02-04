package ru.fivefourtyfive.places.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PolygonPoint(
    @SerializedName("x")
    @Expose
    val x: Double,
    @SerializedName("y")
    @Expose
    val y: Double
)