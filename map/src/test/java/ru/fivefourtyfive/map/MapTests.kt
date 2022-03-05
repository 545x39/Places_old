package ru.fivefourtyfive.map

import org.junit.Test

import org.junit.Assert.*
import ru.fivefourtyfive.map.domain.util.getTileCode

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
    @Test
    fun testTileCodeGen4() {
        assertEquals("012", getTileCode(3, 3, 3))
    }
}