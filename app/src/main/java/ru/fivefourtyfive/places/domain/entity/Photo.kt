package ru.fivefourtyfive.places.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("size")
    @Expose
    val size: Int,
    @SerializedName("status")
    @Expose
    val status: Int,
    @SerializedName("object_id")
    @Expose
    val objectId: Int,
    @SerializedName("user_id")
    @Expose
    val userId: Int,
    @SerializedName("user_name")
    @Expose
    val userName: String? = null,
    @SerializedName("time")
    @Expose
    val time: Long? = null,
    @SerializedName("time_str")
    @Expose
    val timeString: String? = null,
    @SerializedName("last_user_id")
    @Expose
    val lastUserId: Int? = null,
    @SerializedName("last_user_name")
    @Expose
    val lastUserName: String? = null,
    @SerializedName("960_url")
    @Expose
    val url960: String? = null,
    @SerializedName("1280_url")
    @Expose
    val url1280: String? = null,
    @SerializedName("big_url")
    @Expose
    val urlBig: String? = null,
    @SerializedName("thumbnail_url")
    @Expose
    val thumbnailUrl: String? = null,
    @SerializedName("thumbnailRetina_url")
    @Expose
    val thumbnailRetinaUrl: String? = null,
    @SerializedName("user_ip")
    @Expose
    val userIp: String? = null,
    @SerializedName("full_url")
    @Expose
    val fullUrl: String? = null
)