package ru.fivefourtyfive.places.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DebugInfo(
    @SerializedName("code")
    @Expose
    val code: Int? = null,
    @SerializedName("message")
    @Expose
    val message: String? = null
)