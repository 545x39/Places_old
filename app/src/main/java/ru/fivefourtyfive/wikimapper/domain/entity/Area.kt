package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.RU

data class Area(
    @SerializedName("debug")
    @Expose
    var debugInfo: DebugInfo? = null,
    @SerializedName("version")
    @Expose
    val version: Int? = 0,
    @SerializedName("language")
    @Expose
    val language: String? = RU,
    @SerializedName("folder")
    @Expose
    val places: List<Place>? = listOf(),
    @SerializedName("page")
    @Expose
    val page: Int? = 0,
    @SerializedName("count")
    @Expose
    val count: Int? = 0,
    @SerializedName("found")
    @Expose
    val found: Int? = 0
)
