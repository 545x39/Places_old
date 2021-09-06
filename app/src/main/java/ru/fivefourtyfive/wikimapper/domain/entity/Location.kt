package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lon")
    @Expose
    val lon: Double,
    @SerializedName("lat")
    @Expose
    val lat: Double,
    @SerializedName("north")
    @Expose
    val north: Double,
    @SerializedName("south")
    @Expose
    val south: Double,
    @SerializedName("east")
    @Expose
    val east: Double,
    @SerializedName("west")
    @Expose
    val west: Double,
    @SerializedName("country")
    @Expose
    val country: String? = null,
    @SerializedName("state")
    @Expose
    val state: String? = null,
    @SerializedName("place")
    @Expose
    val place: String? = null,
    @SerializedName("city")
    @Expose
    val city: String? = null,
    @Expose
    val zoom: Float? = null
)
