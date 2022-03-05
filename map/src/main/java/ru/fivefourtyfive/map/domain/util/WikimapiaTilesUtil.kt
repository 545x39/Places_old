package ru.fivefourtyfive.map.domain.util

import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import kotlin.math.*

//https://wikimapia.org/z1/itiles/03.xy

private const val LAT_MAX = 85.05113f
private const val LON_MAX = 180.0f
private const val defaultZoomShift = 2
private const val kDegToRad = Math.PI / 180f
private const val kRadToDeg = 180f / Math.PI

fun getTileCode(xLine: Int, yLine: Int, zoom: Int /*, zeroFirst: Boolean */): String {
//    var line = ""
//    if (zeroFirst) line = "0"
    var line = "0"
    var z = zoom - 1
    while (z >= 0) {
        val test = 2.0.pow(z.toDouble()).toInt()
        val xLineTest = xLine and test
        val yLineTest = yLine and test
        val xParam: Int = if (xLineTest > 0) 1 else 0
        val yParam: Int = if (yLineTest > 0) 2 else 0
        val linePart = xParam + yParam
        line += linePart
        z--
    }
    return line
}

@Suppress("SpellCheckingInspection")
private fun dec2merc(coordinate: GeoPoint, zoom: Float): CGPoint {
    val numTiles = 2.0.pow(zoom.toDouble()).toInt()
    val bitmapSize = numTiles * 256 // 256x256 px tile image size
    val bitmapOrigin = (bitmapSize / 2f).toDouble()
    val pixelPerLonDegree = (bitmapSize / 360f).toDouble()
    val pixelPerLonRadian = bitmapSize / (2f * Math.PI)
    val xMerc = floor(bitmapOrigin + coordinate.longitude * pixelPerLonDegree)
    val yMerc: Double =
        if (sin(degToRad(coordinate.latitude)) == 1.0) {
            if (coordinate.latitude > 0) (256f * (numTiles - 1)).toDouble() else 0.0
        } else {
            floor(
                bitmapOrigin - 0.5f *
                        ln(
                            (1 + sin(degToRad(coordinate.latitude)))
                                    / (1 - sin(degToRad(coordinate.latitude)))
                        )
                        * pixelPerLonRadian
            )
        }
    return CGPoint(xMerc, yMerc)
}

@Suppress("SpellCheckingInspection")
fun getWikimapiaTileCodes(bounds: BoundingBox, zoom: Int): List<String> {
    var z = zoom
    z -= defaultZoomShift
    val resultCodes = arrayListOf<String>()
    val minPoint: CGPoint = dec2merc(boundsMinPoint(bounds), z.toFloat())
    val maxPoint: CGPoint = dec2merc(boundsMaxPoint(bounds), z.toFloat())
    val numTiles = 2.0.pow(z.toDouble()).toInt()
    val x1 = (minPoint.x / 256).toInt()
    val x2 = (maxPoint.x / 256).toInt()
    val y1 = (minPoint.y / 256).toInt()
    val y2 = (maxPoint.y / 256).toInt()
    for (i in x1..x2) {
        for (j in y1 downTo y2) {
            resultCodes.add(getTileCode(i, numTiles - j - 1, z /*, true*/))
        }
    }
    return resultCodes
}

/** Return single code for same sw,ne coordinates */
@Suppress("SpellCheckingInspection")
fun getWikimapiaTileCode(coordinate: GeoPoint, zoom: Int) = getWikimapiaTileCodes(
    BoundingBox(
        coordinate.latitude,
        coordinate.longitude,
        coordinate.latitude,
        coordinate.longitude
    ), zoom
)[0]

fun boundsMinPoint(bounds: BoundingBox): GeoPoint {
    val latMin: Double = min(bounds.latNorth, bounds.latSouth)
    val lonMin: Double = min(bounds.lonEast, bounds.lonWest)
    return GeoPoint(latMin, lonMin)
}

fun boundsMaxPoint(bounds: BoundingBox): GeoPoint {
    val latMax: Double = max(bounds.latNorth, bounds.latSouth)
    val lonMax: Double = max(bounds.lonEast, bounds.lonWest)
    return GeoPoint(latMax, lonMax)
}

private fun degToRad(deg: Double): Double {
    return deg * kDegToRad
}

private fun radToDeg(rad: Double): Double {
    return rad * kRadToDeg
}

class CGPoint(var x: Double, var y: Double)
