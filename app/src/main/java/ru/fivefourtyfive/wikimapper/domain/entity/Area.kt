package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("debug")
    @Expose
    var debugInfo: DebugInfo? = null,
    @SerializedName("version")
    @Expose
    val version: Int,
    @SerializedName("language")
    @Expose
    val language: String,
    @SerializedName("folder")
    @Expose
    val places: List<Place>,
    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("count")
    @Expose
    val count: Int,
    @SerializedName("found")
    @Expose
    val found: Int
)
