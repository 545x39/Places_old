package ru.fivefourtyfive.places.domain.entity.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("place_id")
    @Expose
    val placeId: Int,
    @SerializedName("num")
    @Expose
    val num: Int,
    @SerializedName("lang_id")
    @Expose
    val langId: Int,
    @SerializedName("user_id")
    @Expose
    val userId: Int? = null,
    @SerializedName("user_ip")
    @Expose
    val userIp: Int? = null,
    @SerializedName("user_photo")
    @Expose
    val userPhoto: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("good")
    @Expose(deserialize = false)
    val good: Int = 0,
    @SerializedName("bad")
    @Expose(deserialize = false)
    val bad: Int = 0,
    @SerializedName("block")
    @Expose
    val block: Boolean? = false,
    @SerializedName("date")
    @Expose
    val date: Long? = null,
    @SerializedName("moder_uid")
    @Expose(deserialize = false)
    val moderUid: Int? = 0,
    @SerializedName("moder_name")
    @Expose(deserialize = false)
    val moderatorName: String? = null,
    @SerializedName("is_deleted")
    @Expose(deserialize = false)
    val isDeleted: Boolean = false,
//@SerializedName("replies")
//@Expose
//val replies: List<Reply>? = null
)
