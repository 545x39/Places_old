package ru.fivefourtyfive.places.domain.entity.places

@Suppress("unused")
enum class Language {
    RU,
    EN
}

//Old one, might need it.
//data class Language(
//    @SerializedName("lang_id")
//    @Expose
//    val langId: Int,
//    @SerializedName("lang_name")
//    @Expose
//    val langName: String,
//    @SerializedName("object_local_slug")
//    @Expose
//    val objectLocalSlug: String? = null,
//    @SerializedName("native_name")
//    @Expose
//    val nativeName: String? = null,
//    @SerializedName("object_url")
//    @Expose
//    val objectUrl: String? = null
//)