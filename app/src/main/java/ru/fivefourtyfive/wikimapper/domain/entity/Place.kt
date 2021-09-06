package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("object_type")
    @Expose
    val objectType: Int,
    @SerializedName("language_id")
    @Expose
    val languageId: Int,
    @SerializedName("language_iso")
    @Expose
    val languageIso: String,
    @SerializedName("language_name")
    @Expose
    val languageName: String,
    @SerializedName("urlhtml")
    @Expose
    val urlHtml: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("description")
    @Expose
    val description: String? = null,
    ////////
    @SerializedName("wikipedia")
    @Expose
    val wikipedia: String,
    @SerializedName("is_building")
    @Expose
    val isBuilding: Boolean,
    @SerializedName("is_region")
    @Expose
    val isRegion: Boolean,
    @SerializedName("is_deleted")
    @Expose
    val isDeleted: Boolean,
    @SerializedName("tags")
    @Expose
    val tags: List<Tag>? = null,
    @SerializedName("parent_id")
    @Expose
    val parentId: Int? = null,
    @SerializedName("x")
    @Expose
    val x: Int,
    @SerializedName("y")
    @Expose
    val y: Int,
    @SerializedName("pl")
    @Expose
    val pl: Int? = null,
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
/**
Nearest:
private String id;
private Integer languageId;
private String languageIso;
private String languageName;
private String title;
private String url;
private Double lon;
private Double lat;
private Double distance;
 */
/**
 Similar:
 private String id;
 private Integer languageId;
 private String languageIso;
 private String languageName;
 private String title;
 private String url;
 private Double lon;
 private Double lat;
 private Double distance;
 * */