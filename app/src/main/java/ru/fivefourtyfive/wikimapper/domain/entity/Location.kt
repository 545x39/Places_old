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
    @Expose(deserialize = false)
    val place: String? = null,
    @SerializedName("country_adm_id")
    @Expose(deserialize = false)
    val countryAdmId: Int? = null,
//    @SerializedName("gadm")
//    @Expose
//    val gadm: List<Gadm>? = null,
    @SerializedName("city_id")
    @Expose(deserialize = false)
    val cityId: Int? = 0,
    @SerializedName("city")
    @Expose(deserialize = false)
    val city: String? = null,
    @SerializedName("cityguideDomain")
    @Expose(deserialize = false)
    val cityGuideDomain: Any? = null,
    @SerializedName("zoom")
    @Expose(deserialize = false)
    val zoom: Double? = null
)
