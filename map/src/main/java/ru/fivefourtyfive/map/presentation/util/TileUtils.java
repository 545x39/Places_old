package ru.fivefourtyfive.map.presentation.util;

import static ru.fivefourtyfive.wikimapper.util.Network.ROOT_URL;
import static ru.fivefourtyfive.wikimapper.util.Network.WIKIMAPIA_POLYGON_PATH;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TileUtils {

    public static String getUrl(String code) {
        String url = ROOT_URL + WIKIMAPIA_POLYGON_PATH;

        int i = 0;
        while (i < code.length()) {
            url += code.charAt(i);
            if (i == code.length() - 1) {
                url = url + ".xy";
            } else {
                if ((i + 1) % 3 == 0)
                    url = url + "/";
            }
            i++;
        }
        return url;
    }

    public static String wikimapiaTileCodeByCoordinate(IGeoPoint point, int zoom) {
        return wikimapiaTileCodes(new BoundingBox(point.getLatitude(),point.getLongitude(), point.getLatitude(), point.getLongitude()), zoom).get(0);
    }

    public static List<String> wikimapiaTileCodes(IGeoPoint point, int zoom) {
        return wikimapiaTileCodes(new BoundingBox(point.getLatitude(),point.getLongitude(), point.getLatitude(), point.getLongitude()), zoom);
    }

    public static List<String> wikimapiaTileCodes(BoundingBox boundingBox, int paramInt) {
        int zoomParam = paramInt - 2;
        ArrayList<String> arrayList = new ArrayList();
        CGPoint cGPoint2 = decimalToMercator(boundsMinPoint(boundingBox), zoomParam);
        CGPoint cGPoint1 = decimalToMercator(boundsMaxPoint(boundingBox), zoomParam);
        int k = (int) Math.pow(2.0D, zoomParam);
        paramInt = (int) (cGPoint2.x / 256.0D);
        int m = (int) (cGPoint1.x / 256.0D);
        int i = (int) (cGPoint2.y / 256.0D);
        int n = (int) (cGPoint1.y / 256.0D);
        while (paramInt <= m) {
            for (int i1 = i; i1 >= n; i1--) {
                arrayList.add(XYToCode(paramInt, k - i1 - 1, zoomParam, true));
            }
            paramInt++;
        }
        return arrayList;
    }


    public static GeoPoint boundsMaxPoint(BoundingBox box) {
        double maxLongitude = Math.max(
                limit(box.getLonEast())//box.northeast.longitude
                ,
                limit(box.getLonWest())//box.southwest.longitude
        );
        return new GeoPoint(Math.max(
                limit(box.getLatNorth()),limit(box.getLatSouth())), maxLongitude);
    }

    public static GeoPoint boundsMinPoint(BoundingBox box) {
        double minLongitude = Math.min(limit(box.getLonEast()),limit(box.getLonWest()));
        return new GeoPoint(Math.min(limit(box.getLatNorth()),limit(box.getLatSouth())), minLongitude);
    }


    private static Double limit(Double value) {
//        DecimalFormat numberFormat = new DecimalFormat("#.00");
//        return Double.parseDouble(numberFormat.format(value));
        return value;
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

    public static CGPoint decimalToMercator(GeoPoint point, float paramFloat) {
        int i = (int) Math.pow(2.0D, paramFloat);
        int j = i * 256;
        double d1 = (j / 2.0F);
        double d2 = (j / 360.0F);
        double d3 = j / 6.283185307179586D;
        d2 = Math.floor(point.getLongitude() * d2 + d1);
        if (Math.sin(CommonDegRad.degToRad(point.getLatitude())) == 1.0D) {
            if (point.getLatitude() > 0.0D) {
                d1 = (256.0F * (i - 1));
                return new CGPoint(d2, d1);
            }
            d1 = 0.0D;
            return new CGPoint(d2, d1);
        }
        d1 = Math.floor(d1 - 0.5D * Math.log((1.0D + Math.sin(CommonDegRad.degToRad(point.getLatitude()))) / (1.0D - Math.sin(CommonDegRad.degToRad(point.getLatitude())))) * d3);
        return new CGPoint(d2, d1);
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
