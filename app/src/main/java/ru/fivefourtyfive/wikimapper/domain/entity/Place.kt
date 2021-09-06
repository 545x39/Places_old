package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("debug")
    @Expose
    var debugInfo: DebugInfo? = null,
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("object_type")
    @Expose
    val objectType: Int = 0,
    @SerializedName("language_id")
    @Expose
    val languageId: Int = 0,
    @SerializedName("language_iso")
    @Expose
    val languageIso: String? = null,
    @SerializedName("language_name")
    @Expose
    val languageName: String? = null,
    @SerializedName("urlhtml")
    @Expose
    val urlHtml: String? = null,
    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("description")
    @Expose
    val description: String? = null,
    ////////
    @SerializedName("wikipedia")
    @Expose
    val wikipedia: String? = null,
    @SerializedName("is_building")
    @Expose
    val isBuilding: Boolean = false,
    @SerializedName("is_region")
    @Expose
    val isRegion: Boolean = false,
    @SerializedName("is_deleted")
    @Expose
    val isDeleted: Boolean = false,
    @SerializedName("tags")
    @Expose
    val tags: List<Tag>? = null,
    @SerializedName("parent_id")
    @Expose
    val parentId: Int? = null,
    @SerializedName("x")
    @Expose
    val x: Int? = null,
    @SerializedName("y")
    @Expose
    val y: Int? = null,
    @SerializedName("pl")
    @Expose
    val pl: Double? = null,
    @SerializedName("polygon")
    @Expose
    val polygon: List<Dot>? = null,
    @SerializedName("is_protected")
    @Expose(deserialize = false)
    val isProtected: Boolean? = null,
    @SerializedName("photos")
    @Expose
    val photos: List<Photo>? = null,
    @SerializedName("comments")
    @Expose
    val comments: List<Comment>? = null,
    @SerializedName("location")
    @Expose
    val location: Location? = null,
//    @SerializedName("availableLanguages")
//    @Expose
//    val availableLanguages: AvailableLanguages? = null,
//    @SerializedName("similarPlaces")
//    @Expose
//    val similarPlaces: SimilarPlaces? = null,
//    @SerializedName("nearestPlaces")
//    @Expose
//    val nearestPlaces: NearestPlaces? = null,
//    @SerializedName("nearestHotels")
//    @Expose
//    val nearestHotels: List<Object>? = null
//    @SerializedName("edit_info")
//    @Expose
//    val editInfo: EditInfo,
)