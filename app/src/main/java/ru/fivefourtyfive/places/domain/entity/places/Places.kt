package ru.fivefourtyfive.places.domain.entity.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value.RU

data class Places(
    @SerializedName("debug")
    @Expose
    var debugInfo: DebugInfo? = null,
    @SerializedName("version")
    @Expose
    val version: String? = "",
    @SerializedName("language")
    @Expose
    val language: String? = RU,
    @SerializedName("folder")
    @Expose
    val places: List<Place>? = listOf(),
    @SerializedName("page")
    @Expose
    val page: Int? = 1,
    @SerializedName("count")
    @Expose
    val count: Int? = 0,
    @SerializedName("found")
    @Expose
    val found: Int? = 0
)
