@file:Suppress("SpellCheckingInspection")

package ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Function.CATEGORY_GET_ALL
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Function.CATEGORY_GET_BY_ID
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Function.OBJECT_GET_BY_BOX
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Function.OBJECT_GET_BY_ID
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Function.SEARCH
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.BBOX
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.CATEGORY
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.COORDS_BY
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.COUNT
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.DATA_BLOCKS
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.FORMAT
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.FUNCTION
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.ID
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.KEY
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.LANGUAGE
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.LATITUDE
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.LONGITUDE
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.NAME
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.PACK
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.PAGE
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Parameter.QUERY
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.API_KEY
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.DEFAULT_BOX
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.DEFAULT_PAGE
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.GZIP
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.JSON
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.MAX_OBJECTS_PER_PAGE
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.Value.RU
import ru.fivefourtyfive.wikimapper.domain.entity.Categories
import ru.fivefourtyfive.wikimapper.domain.entity.Category
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.entity.Places
import ru.fivefourtyfive.wikimapper.util.Network.ROOT_URL
import ru.fivefourtyfive.wikimapper.util.Network.WIKIMAPIA_POLYGON_PATH

interface Api {

    @GET(".")
    @Headers(
        "Content-Type: Application/Raw",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    suspend fun getPlace(
        @Query(value = KEY)         key:            String = API_KEY,
        @Query(value = FUNCTION)    function:       String = OBJECT_GET_BY_ID,
        @Query(value = ID)          id: Int,
        @Query(value = DATA_BLOCKS) dataBlocks:     String? = null,
        @Query(value = LANGUAGE)    language:       String? = RU,
        @Query(value = PACK)        pack:           String? = GZIP,
        @Query(value = FORMAT)      format:         String  = JSON,
    ): Place

    @GET(".")
    @Headers(
        "Content-Type: Application/Raw",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    suspend fun getArea(
        @Query(value = KEY)         key:            String  = API_KEY,
        @Query(value = FUNCTION)    function:       String  = OBJECT_GET_BY_BOX,
        @Query(value = COORDS_BY)   coordsBy:       String  = BBOX,
        @Query(value = BBOX)        boundingBox:    String  = DEFAULT_BOX,
        @Query(value = CATEGORY)    category:       String? = null,
        @Query(value = PAGE)        page:           Int?    = DEFAULT_PAGE,
        @Query(value = COUNT)       count:          Int?    = MAX_OBJECTS_PER_PAGE,
        @Query(value = LANGUAGE)    language:       String? = RU,
        @Query(value = PACK)        pack:           String? = GZIP,
        @Query(value = FORMAT)      format:         String  = JSON,
    ): Places

    @GET(".")
    @Headers(
        "Content-Type: Application/Raw",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun getCategories(
        @Query(value = KEY)         key:            String  = API_KEY,
        @Query(value = FUNCTION)    function:       String  = CATEGORY_GET_ALL,
        @Query(value = NAME)        name:           String? = null,
        @Query(value = PAGE)        page:           Int?    = DEFAULT_PAGE,
        @Query(value = COUNT)       count:          Int?    = MAX_OBJECTS_PER_PAGE,
        @Query(value = LANGUAGE)    language:       String? = RU,
        @Query(value = PACK)        pack:           String? = GZIP,
        @Query(value = FORMAT)      format:         String  = JSON
    ): Categories

    @GET(".")
    @Headers(
        "Content-Type: Application/Raw",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun getCategory(
        @Query(value = KEY)         key:            String  = API_KEY,
        @Query(value = FUNCTION)    function:       String  = CATEGORY_GET_BY_ID,
        @Query(value = ID)          id: Int,
        @Query(value = LANGUAGE)    language:       String? = RU,
        @Query(value = PACK)        pack:           String? = GZIP,
        @Query(value = FORMAT)      format:         String  = JSON
    ): Category

    @GET(".")
    @Headers(
        "Content-Type: Application/Raw",
        "Accept: application/json;charset=utf-8",
        "Cache-Control: max-age=640000"
    )
    fun search(
        @Query(value = KEY)         key:            String  = API_KEY,
        @Query(value = FUNCTION)    function:       String  = SEARCH,
        @Query(value = QUERY)       query:          String,
        @Query(value = LATITUDE)    latitude:       Float,
        @Query(value = LONGITUDE)   longitude:      Float,
        @Query(value = CATEGORY)    category:       String? = null,
        @Query(value = PAGE)        page:           Int?    = DEFAULT_PAGE,
        @Query(value = COUNT)       count:          Int?    = MAX_OBJECTS_PER_PAGE,
        @Query(value = LANGUAGE)    language:       String? = RU,
        @Query(value = PACK)        pack:           String? = GZIP,
        @Query(value = FORMAT)      format:         String  = JSON
    ): Places

    @GET("$ROOT_URL$WIKIMAPIA_POLYGON_PATH{codes}")
    @Headers(
        "Content-Type: Application/Raw",
        "Cache-Control: max-age=640000"
    )
    fun getArea(
        @Path("codes") codes: String
    ): Call<ResponseBody>
}