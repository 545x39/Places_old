package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/** Data sent in both nearest_places and similar_places is identical. */
/** Not going to request this stuff so far yet.*/
data class RelatedPlace(
    @SerializedName("id")
    @Expose
    val placeId: Int,
    @SerializedName("language_id")
    @Expose
    val languageId: Int,
    @SerializedName("language_iso")
    @Expose
    val languageIso: String,
    @SerializedName("language_name")
    @Expose
    val languageName: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("lon")
    @Expose
    val lon: Double,
    @SerializedName("lat")
    @Expose
    val lat: Double,
    @SerializedName("distance")
    @Expose
    val distance: Double
)
