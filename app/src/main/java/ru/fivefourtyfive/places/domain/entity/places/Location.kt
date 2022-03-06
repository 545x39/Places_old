package ru.fivefourtyfive.places.domain.entity.places

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.util.TableName.TABLE_LOCATIONS
import ru.fivefourtyfive.places.domain.entity.util.ID
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.CITY
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.CITY_ID
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.COUNTRY
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.COUNTRY_ADM_ID
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.EAST
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.LAT
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.LON
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.NORTH
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.PLACE
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.PLACE_ID
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.SOUTH
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.STATE
import ru.fivefourtyfive.places.domain.entity.util.LocationFields.WEST
import ru.fivefourtyfive.places.domain.entity.util.PlaceFields

@Entity(
    tableName = TABLE_LOCATIONS,
    foreignKeys = [ForeignKey(
        entity = Place::class,
        parentColumns = [ID],
        childColumns = [PLACE_ID],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = [ID], unique = true), Index(value = [PLACE_ID])]
)
data class Location(
    @ColumnInfo(name = ID)
    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = LON)
    @SerializedName(LON)
    @Expose
    val lon: Double,
    @ColumnInfo(name = LAT)
    @SerializedName(LAT)
    @Expose
    val lat: Double,
    @ColumnInfo(name = NORTH)
    @SerializedName(NORTH)
    @Expose
    val north: Double,
    @ColumnInfo(name = SOUTH)
    @SerializedName(SOUTH)
    @Expose
    val south: Double,
    @ColumnInfo(name = EAST)
    @SerializedName(EAST)
    @Expose
    val east: Double,
    @ColumnInfo(name = WEST)
    @SerializedName(WEST)
    @Expose
    val west: Double,
    @ColumnInfo(name = COUNTRY)
    @SerializedName(COUNTRY)
    @Expose
    val country: String? = null,
    @ColumnInfo(name = STATE)
    @SerializedName(STATE)
    @Expose
    val state: String? = null,
    @ColumnInfo(name = PLACE)
    @SerializedName(PLACE)
    @Expose
    val place: String? = null,
    @ColumnInfo(name = COUNTRY_ADM_ID)
    @SerializedName(COUNTRY_ADM_ID)
    @Expose
    val countryAdmId: Int? = null,
//    @SerializedName("gadm")
//    @Expose
//    val gadm: List<Gadm>? = null,
    @ColumnInfo(name = CITY_ID)
    @SerializedName(CITY_ID)
    @Expose
    val cityId: Int? = 0,
    @ColumnInfo(name = CITY)
    @SerializedName(CITY)
    @Expose
    val city: String? = null,
//    @ColumnInfo(name = CITY_GUIDE_DOMAIN)
//    @SerializedName(CITY_GUIDE_DOMAIN)
//    @Expose
//    val cityGuideDomain: Any? = null,
    @SerializedName("zoom")
    @Expose
    val zoom: Double? = null
) {
    @ColumnInfo(name = PLACE_ID)
    var placeId: Int? = null
}
