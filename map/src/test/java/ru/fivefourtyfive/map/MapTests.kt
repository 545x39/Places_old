package ru.fivefourtyfive.map

import org.junit.Test

import org.junit.Assert.*
import org.osmdroid.util.BoundingBox
import ru.fivefourtyfive.map.domain.util.getTileCode
import ru.fivefourtyfive.map.domain.util.getWikimapiaTileCodes
import timber.log.Timber

class MapTests {
    @Test
    fun testTileCodeGen1() {
        assertEquals("021", getTileCode(22, 22, 4))
    }

    @Test
    fun testTileCodeGen2() {
        assertEquals("003", getTileCode(2, 3, 3))
    }

    @Test
    fun testTileCodeGen3() {
        assertEquals("030", getTileCode(3, 2, 3))
    }

    //west: 29.618307026565247, north: 60.0925847405053, east: 29.85423821471457, south: 59.88368772872185
    //ZOOM 11.39741674571779
    @Test
    fun testTileCodeGen4() {
        val box = BoundingBox(60.0925847405053, 29.85423821471457, 59.88368772872185, 29.618307026565247)
        getWikimapiaTileCodes(box, 12).forEach{
            System.out.println("CODE: $it")
            assertEquals("012", it)
        }
//        assertEquals("012", getTileCode(3, 3, 3))
    }
}