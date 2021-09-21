package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PolyPoint(
    @SerializedName("x")
    @Expose
    val x: Double,
    @SerializedName("y")
    @Expose
    val y: Double
)