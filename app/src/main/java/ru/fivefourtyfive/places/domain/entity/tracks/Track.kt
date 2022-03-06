package ru.fivefourtyfive.places.domain.entity.tracks

import androidx.room.*
import ru.fivefourtyfive.places.domain.entity.util.ID
import ru.fivefourtyfive.places.domain.entity.util.NAME
import ru.fivefourtyfive.places.domain.entity.util.TrackFields.ELAPSED_TIME
import ru.fivefourtyfive.places.domain.entity.util.TrackFields.END_TIME
import ru.fivefourtyfive.places.domain.entity.util.TrackFields.IS_RECORDING
import ru.fivefourtyfive.places.domain.entity.util.TrackFields.START_TIME
import ru.fivefourtyfive.places.framework.datasource.implementation.local.database.util.TableName.TABLE_TRACKS

@Entity(tableName = TABLE_TRACKS,
    indices = [Index(value = [ID], unique = true)]
)
data class Track(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = NAME)
    var name: String = "",
    @ColumnInfo(name = ELAPSED_TIME)
    var duration: Long? = null,
    @ColumnInfo(name = START_TIME)
    var startTime: Long? = null,
    @ColumnInfo(name = END_TIME)
    var endTime: Long? = null,
    @ColumnInfo(name = IS_RECORDING)
    var isRecording: Boolean? = null
){
    @Ignore
    var path: List<TrackPoint> = listOf()
}