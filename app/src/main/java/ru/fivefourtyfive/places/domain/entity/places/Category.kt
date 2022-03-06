package ru.fivefourtyfive.places.domain.entity.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("debug")
    @Expose
    var debugInfo: DebugInfo? = null,
    @SerializedName("id")
    @Expose
    val id: Int? = 0,
    @SerializedName("amount")
    @Expose
    val amount: Int? = 0,
    @SerializedName("icon")
    @Expose
    val icon: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("description")
    @Expose
    val description: String? = null,
    @SerializedName("synonyms")
    @Expose
    val synonyms: List<Category>? = null
)
