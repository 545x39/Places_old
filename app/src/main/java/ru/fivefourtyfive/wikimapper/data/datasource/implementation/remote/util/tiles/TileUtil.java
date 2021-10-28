package ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util.tiles;

import java.util.ArrayList;
import java.util.List;

public class TileUtil {

    //paramInt must be zoom
    public static String wikimapiaTileCodeByCoordinate(Coordinates paramCoordinates, int paramInt) {
        return wikimapiaTileCodes(new BoundingCoordinates(paramCoordinates, paramCoordinates), paramInt).get(0);
    }

    public static List<String> wikimapiaTileCodes(Double latMin, Double lonMin, Double latMax, Double lonMax, int zoom) {
        return wikimapiaTileCodes(new BoundingCoordinates(new Coordinates(latMin, lonMin), new Coordinates(latMax, lonMax)), zoom);
    }

    public static List<String> wikimapiaTileCodesExtended(Double latMin, Double lonMin, Double latMax, Double lonMax, int zoom) {
        return wikimapiaTileCodesExtended(new BoundingCoordinates(new Coordinates(latMin, lonMin), new Coordinates(latMax, lonMax)), zoom);
    }

    //paramInt must be zoom
    public static List<String> wikimapiaTileCodes(BoundingCoordinates paramBoundingCoordinates, int paramInt) {
        int j = paramInt - 2;
        ArrayList<String> arrayList = new ArrayList();
        CGPoint cGPoint2 = decimalToMercator(boundsMinPoint(paramBoundingCoordinates), j);
        CGPoint cGPoint1 = decimalToMercator(boundsMaxPoint(paramBoundingCoordinates), j);
        int k = (int) Math.pow(2.0D, j);
        paramInt = (int) (cGPoint2.x / 256.0D);
        int m = (int) (cGPoint1.x / 256.0D);
        int i = (int) (cGPoint2.y / 256.0D);
        int n = (int) (cGPoint1.y / 256.0D);
        while (paramInt <= m) {
            for (int i1 = i; i1 >= n; i1--) {
                arrayList.add(XYToCode(paramInt, k - i1 - 1, j, true));
            }
            paramInt++;
        }
        return arrayList;
    }

    public static Coordinates boundsMinPoint(BoundingCoordinates paramBoundingCoordinates) {
        return new Coordinates(Math.min(paramBoundingCoordinates.northeast.latitude, paramBoundingCoordinates.southwest.latitude)
                , Math.min(paramBoundingCoordinates.northeast.longitude, paramBoundingCoordinates.southwest.longitude));
    }

    public static Coordinates boundsMaxPoint(BoundingCoordinates box) {
        return new Coordinates(Math.max(box.northeast.latitude, box.southwest.latitude)
                , Math.max(box.northeast.longitude, box.southwest.longitude));
    }

    public static CGPoint decimalToMercator(Coordinates paramCoordinates, float paramFloat) {
        int i = (int) Math.pow(2.0D, paramFloat);
        int j = i * 256;
        double d1 = (j / 2.0F);
        double d2 = (j / 360.0F);
        double d3 = j / 6.283185307179586D;
        d2 = Math.floor(paramCoordinates.longitude * d2 + d1);
        if (Math.sin(CommonDegRad.degToRad(paramCoordinates.latitude)) == 1.0D) {
            if (paramCoordinates.latitude > 0.0D) {
                d1 = (256.0F * (i - 1));
                return new CGPoint(d2, d1);
            }
            d1 = 0.0D;
            return new CGPoint(d2, d1);
        }
        d1 = Math.floor(d1 - 0.5D * Math.log((1.0D + Math.sin(CommonDegRad.degToRad(paramCoordinates.latitude))) / (1.0D - Math.sin(CommonDegRad.degToRad(paramCoordinates.latitude)))) * d3);
        return new CGPoint(d2, d1);
    }

    //paramInt must be zoom
    public static List<String> wikimapiaTileCodesExtended(BoundingCoordinates box, int paramInt) {
        double d1 = (box.northeast.longitude - box.southwest.longitude) / 10.0D;
        double d2 = (box.northeast.latitude - box.southwest.latitude) / 10.0D;
        double d3 = box.northeast.latitude;
        double d4 = box.northeast.longitude;
        double d5 = box.southwest.latitude;
        double d6 = box.southwest.longitude;
        return wikimapiaTileCodes(new BoundingCoordinates(new Coordinates(limitVal(d3 + d2, 85.05113220214844D), limitVal(d4 + d1, 180.0D)), new Coordinates(limitVal(d5 - d2, 85.05113220214844D), limitVal(d6 - d1, 180.0D))), paramInt);
    }

    public static double limitVal(double value1, double value2) {
        if (value1 > 0.0D) return Math.min(value1, value2);
        double d = value1;
        if (value1 < -value2)
            d = -value2;
        return d;
    }

    public static String XYToCode(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
        String str = "";
        if (paramBoolean)
            str = "0";
        while (--paramInt3 >= 0) {
            byte b;
            int i = (int) Math.pow(2.0D, paramInt3);
            if ((paramInt1 & i) > 0) {
                b = 1;
            } else {
                b = 0;
            }
            if ((paramInt2 & i) > 0) {
                i = 2;
            } else {
                i = 0;
            }
            str = str + (b + i);
            paramInt3--;
        }
        return str;
    }

    public static class CGPoint {

        public double x;
        public double y;

        public CGPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class CommonDegRad {

        public static final double degToRad = 0.017453292519943295D;
        public static final double radToDeg = 57.29577951308232D;

        public static double degToRad(double paramDouble) {
            return degToRad * paramDouble;
        }

        public static double radToDeg(double paramDouble) {
            return radToDeg * paramDouble;
        }
    }
}
