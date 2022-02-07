package ru.fivefourtyfive.places.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("debug")
    @Expose
    var debugInfo: DebugInfo? = null,
    @SerializedName("categories")
    @Expose
    val categories: List<Category>? = listOf(),
    @SerializedName("found")
    @Expose
    val found: Int? = 0,
    @SerializedName("page")
    @Expose
    val page: Int? = 1,
    @SerializedName("count")
    @Expose
    val count: Int? = 0
)
