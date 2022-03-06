package ru.fivefourtyfive.places.framework.datasource.implementation.local.database.util

object Version{
    const val DB_VERSION = 3
}

object Path {
    const val DB_DIR                   = "db"
    const val DB_FILENAME              = "places.db"
}

object TableName{
    const val TABLE_PLACES              = "places"
    const val TABLE_TAGS                = "tags"
    const val TABLE_COMMENTS            = "comments"
    const val TABLE_POINTS              = "points"
    const val TABLE_PHOTOS              = "photos"
    const val TABLE_LOCATIONS           = "locations"
    const val TABLE_TRACKS              = "tracks"
    const val TABLE_TRACK_POINTS        = "track_points"

}
