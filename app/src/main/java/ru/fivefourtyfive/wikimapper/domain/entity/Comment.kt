package ru.fivefourtyfive.wikimapper.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("place_id")
    @Expose
    val placeId: String,
    @SerializedName("num")
    @Expose
    val num: Int,
    @SerializedName("user_photo")
    @Expose
    val userPhoto: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("block")
    @Expose
    val block: Boolean? = false,
    @SerializedName("date")
    @Expose
    val date: Long? = null,
    @SerializedName("is_deleted")
    @Expose
    val isDeleted: Boolean = false,
)
