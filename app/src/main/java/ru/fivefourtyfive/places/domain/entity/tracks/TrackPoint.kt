package ru.fivefourtyfive.places.domain.entity.tracks

import androidx.room.*
import ru.fivefourtyfive.places.domain.entity.util.ID
import ru.fivefourtyfive.places.domain.entity.util.PlaceFields
import ru.fivefourtyfive.places.domain.entity.util.TrackFields.ELAPSED_TIME
import ru.fivefourtyfive.places.domain.entity.util.TrackFields.SPEED
import ru.fivefourtyfive.places.domain.entity.util.TrackFields.TRACK_ID
import ru.fivefourtyfive.places.domain.entity.util.X
import ru.fivefourtyfive.places.domain.entity.util.Y
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.util.TableName

@Entity(
    tableName = TableName.TABLE_TRACK_POINTS,
    foreignKeys = [ForeignKey(
        entity = Track::class,
        parentColumns = [ID],
        childColumns = [TRACK_ID],
        onUpdate = ForeignKey.NO_ACTION,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = [ID], unique = true), Index(value = [TRACK_ID])]
)
data class TrackPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = TRACK_ID)
    val trackId: Int,
    @ColumnInfo(name = X)
    val x: Double,
    @ColumnInfo(name = Y)
    val y: Double,
    @ColumnInfo(name = SPEED)
    val speed: Float,
    @ColumnInfo(name = ELAPSED_TIME)
    val elapsedTime: Long,
)

