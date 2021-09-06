@file:Suppress("SpellCheckingInspection")

package ru.fivefourtyfive.wikimapper.data.datasource.remote.util

object Parameter {
    const val FUNCTION          = "function"
    const val KEY               = "key"
    const val ID                = "id"
    const val FORMAT            = "format"
    const val COORDS_BY         = "coordsby"
    const val PACK              = "pack"
    const val LANGUAGE          = "language" //ISO 639-1 format.
    const val BBOX              = "bbox"
    const val CATEGORY          = "category"
    const val COUNT             = "count"
    const val NAME              = "name"
    const val PAGE              = "page"
    const val QUERY             = "q"
    const val LATITUDE          = "lat"
    const val LONGITUDE         = "lon"
    const val DISTANCE          = "distance"
    const val DATA_BLOCKS       = "data_blocks"

}

object Function {
    const val OBJECT_GET_BY_BOX = "box"
    const val OBJECT_GET_BY_ID  = "place.getbyid"
    const val CATEGORY_GET_ALL  = "category.getall"
    const val SEARCH            = "search"
}

object Value {
    const val API_KEY           = "example"
    const val JSON              = "json"
    const val RU                = "ru"
    const val GZIP              = "gzip"
    ////
    const val DEFAULT_BOX       = "35.2886,13.9666,C35.5152,14.1093"
}

object DataBlock {
    const val MAIN              = "main"
    const val GEOMETRY          = "geometry"
    const val EDIT              = "edit"
    const val LOCATION          = "location"
    const val ATTACHED          = "attached"
    const val PHOTOS            = "photos"
    const val COMMENTS          = "comments"
    const val TRANSLATE         = "translate"
    const val SIMILAR_OBJECTS   = "similar_places"
    const val NEAREST_OBJECTS   = "nearest_places"
    const val NEAREST_COMMENTS  = "nearest_comments"
    const val NEAREST_STREETS   = "nearest_streets"
    const val NEAREST_HOTELS    = "nearest_hotels"
}