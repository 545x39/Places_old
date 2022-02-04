package ru.fivefourtyfive.wikimapper.domain.entity

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.fivefourtyfive.wikimapper.framework.datasource.implementation.local.database.typeconverter.PolygonConverter
import ru.fivefourtyfive.wikimapper.framework.datasource.implementation.local.database.util.TableName.TABLE_PLACES
import ru.fivefourtyfive.wikimapper.domain.entity.util.ID
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.COMMENTS
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.DEBUG_INFO
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.DESCRIPTION
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.DISTANCE
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.IS_BUILDING
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.IS_DELETED
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.IS_PROTECTED
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.IS_REGION
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.LANGUAGE_ID
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.LANGUAGE_ISO
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.LANGUAGE_NAME
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.LOCATION
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.NAME
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.OBJECT_TYPE
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.PARENT_ID
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.PHOTOS
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.PL
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.POLYGON
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.SQUARE
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.TAGS
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.TITLE
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.URL
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.URL_HTML
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.WIKIPEDIA
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.X
import ru.fivefourtyfive.wikimapper.domain.entity.util.PlaceFields.Y

@Entity(
    tableName = TABLE_PLACES,
    indices = [Index(value = [ID], unique = true), Index(value = [POLYGON])]
)
@TypeConverters(PolygonConverter::class)
data class Place(
    @ColumnInfo(name = ID)
    @SerializedName(ID)
    @Expose
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = OBJECT_TYPE)
    @SerializedName(OBJECT_TYPE)
    @Expose
    val objectType: Int = 0,
    @ColumnInfo(name = LANGUAGE_ID)
    @SerializedName(LANGUAGE_ID)
    @Expose
    val languageId: Int = 0,
    @ColumnInfo(name = LANGUAGE_ISO)
    @SerializedName(LANGUAGE_ISO)
    @Expose
    val languageIso: String? = null,
    @ColumnInfo(name = LANGUAGE_NAME)
    @SerializedName(LANGUAGE_NAME)
    @Expose
    val languageName: String? = null,
    @ColumnInfo(name = URL)
    @SerializedName(URL)
    @Expose
    val url: String? = null,
    @ColumnInfo(name = URL_HTML)
    @SerializedName(URL_HTML)
    @Expose
    val urlHtml: String? = null,
    @ColumnInfo(name = TITLE)
    @SerializedName(TITLE)
    @Expose
    val title: String? = null,
    @ColumnInfo(name = NAME)
    @SerializedName(NAME)
    @Expose
    val name: String? = null,
    @ColumnInfo(name = DESCRIPTION)
    @SerializedName(DESCRIPTION)
    @Expose
    val description: String? = null,
    @ColumnInfo(name = WIKIPEDIA)
    @SerializedName(WIKIPEDIA)
    @Expose
    val wikipedia: String? = null,
    @SerializedName(POLYGON)
    @Expose
    val polygon: List<PolygonPoint>? = null,
    @ColumnInfo(name = PARENT_ID)
    @SerializedName(PARENT_ID)
    @Expose
    val parentId: Int? = null,
    @ColumnInfo(name = IS_BUILDING)
    @SerializedName(IS_BUILDING)
    @Expose
    val isBuilding: Boolean = false,
    @ColumnInfo(name = IS_REGION)
    @SerializedName(IS_REGION)
    @Expose
    val isRegion: Boolean = false,
    @ColumnInfo(name = IS_DELETED)
    @SerializedName(IS_DELETED)
    @Expose
    val isDeleted: Boolean = false,
    @ColumnInfo(name = X)
    @SerializedName(X)
    @Expose
    val x: Int? = null,
    @ColumnInfo(name = Y)
    @SerializedName(Y)
    @Expose
    val y: Int? = null,
    /** Площадь объекта */
    @ColumnInfo(name = SQUARE)
    @SerializedName(PL)
    @Expose
    val square: Double? = null,
    @ColumnInfo(name = IS_PROTECTED)
    @SerializedName(IS_PROTECTED)
    @Expose(deserialize = false)
    val isProtected: Boolean? = null,
    /** Приходит только в поиске. */
//    @Ignore
    //@ColumnInfo(name = DISTANCE)
    @SerializedName(DISTANCE)
    @Expose
    val distance: Int? = null,
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
) {
    @SerializedName(DEBUG_INFO)
    @Expose
    @Ignore
    var debugInfo: DebugInfo? = null

    @SerializedName(LOCATION)
    @Expose
    @Ignore
    var location: Location? = null

    @SerializedName(TAGS)
    @Expose
    @Ignore
    var tags: List<Tag>? = null

    @SerializedName(PHOTOS)
    @Expose
    @Ignore
    var photos: List<Photo>? = null

    @SerializedName(COMMENTS)
    @Expose
    @Ignore
    var comments: List<Comment>? = null
}
