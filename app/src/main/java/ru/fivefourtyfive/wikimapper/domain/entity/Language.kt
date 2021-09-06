package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("lang_id")
    @Expose
    val langId: Int,
    @SerializedName("lang_name")
    @Expose
    val langName: String,
    @SerializedName("object_local_slug")
    @Expose
    val objectLocalSlug: String? = null,
    @SerializedName("native_name")
    @Expose
    val nativeName: String? = null,
    @SerializedName("object_url")
    @Expose
    val objectUrl: String? = null
)
