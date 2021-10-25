package ru.fivefourtyfive.wikimapper.util;

import java.util.ArrayList;
import java.util.List;

public class TileUtil {

    /*

    //paramInt must be zoom
    public static String wikimapiaTileCodeByCoordinate(LatLng paramLatLng, int paramInt) {
        return wikimapiaTileCodes(new LatLngBounds(paramLatLng, paramLatLng), paramInt).get(0);
    }

    //paramInt must be zoom
    public static List<String> wikimapiaTileCodes(LatLngBounds paramLatLngBounds, int paramInt) {
        int j = paramInt - 2;
        ArrayList<String> arrayList = new ArrayList();
        CGPoint cGPoint2 = dec2merc(boundsMinPoint(paramLatLngBounds), j);
        CGPoint cGPoint1 = dec2merc(boundsMaxPoint(paramLatLngBounds), j);
        int k = (int) Math.pow(2.0D, j);
        paramInt = (int) (cGPoint2.x / 256.0D);
        int m = (int) (cGPoint1.x / 256.0D);
        int i = (int) (cGPoint2.y / 256.0D);
        int n = (int) (cGPoint1.y / 256.0D);
        while (paramInt <= m) {
            for (int i1 = i; i1 >= n; i1--)
                arrayList.add(XYToCode(paramInt, k - i1 - 1, j, true));
            paramInt++;
        }
        return arrayList;
    }

    public static CGPoint dec2merc(LatLng paramLatLng, float paramFloat) {
        int i = (int)Math.pow(2.0D, paramFloat);
        int j = i * 256;
        double d1 = (j / 2.0F);
        double d2 = (j / 360.0F);
        double d3 = j / 6.283185307179586D;
        d2 = Math.floor(paramLatLng.longitude * d2 + d1);
        if (Math.sin(CommonDegRad.degToRad(paramLatLng.latitude)) == 1.0D) {
            if (paramLatLng.latitude > 0.0D) {
                d1 = (256.0F * (i - 1));
                return new CGPoint(d2, d1);
            }
            d1 = 0.0D;
            return new CGPoint(d2, d1);
        }
        d1 = Math.floor(d1 - 0.5D * Math.log((1.0D + Math.sin(CommonDegRad.degToRad(paramLatLng.latitude))) / (1.0D - Math.sin(CommonDegRad.degToRad(paramLatLng.latitude)))) * d3);
        return new CGPoint(d2, d1);
    }

    //paramInt must be zoom
    public static List<String> wikimapiaTileCodesExtended(LatLngBounds paramLatLngBounds, int paramInt) {
        double d1 = (paramLatLngBounds.northeast.longitude - paramLatLngBounds.southwest.longitude) / 10.0D;
        double d2 = (paramLatLngBounds.northeast.latitude - paramLatLngBounds.southwest.latitude) / 10.0D;
        double d3 = paramLatLngBounds.northeast.latitude;
        double d4 = paramLatLngBounds.northeast.longitude;
        double d5 = paramLatLngBounds.southwest.latitude;
        double d6 = paramLatLngBounds.southwest.longitude;
        return wikimapiaTileCodes(new LatLngBounds(new LatLng(limitVal(d3 + d2, 85.05113220214844D), limitVal(d4 + d1, 180.0D)), new LatLng(limitVal(d5 - d2, 85.05113220214844D), limitVal(d6 - d1, 180.0D))), paramInt);
    }

     */
    public static double limitVal(double paramDouble1, double paramDouble2) {
        if (paramDouble1 > 0.0D) return Math.min(paramDouble1, paramDouble2);
        double d = paramDouble1;
        if (paramDouble1 < -paramDouble2)
            d = -paramDouble2;
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

        public CGPoint(double paramDouble1, double paramDouble2) {
            this.x = paramDouble1;
            this.y = paramDouble2;
        }
    }

    public static class CommonDegRad {
        public static final double kDegToRad = 0.017453292519943295D;

        public static final double kRadToDeg = 57.29577951308232D;

        public static double degToRad(double paramDouble) {
            return kDegToRad * paramDouble;
        }

        public static double radToDeg(double paramDouble) {
            return kRadToDeg * paramDouble;
        }
    }
}
