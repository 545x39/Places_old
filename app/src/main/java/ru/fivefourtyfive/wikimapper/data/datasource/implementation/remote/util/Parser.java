package ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public static final String DELIMETR_POLYGONS = "|";

    public static final String PROBLEM = "\\";

//    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    public static List<PolyPoint> newDecodedBinaryPolygon(String paramString) {
        ArrayList<PolyPoint> arrayList = new ArrayList();
        int j = paramString.length();
        int i = 0;
        byte[] arrayOfByte = paramString.getBytes(Charset.forName("US-ASCII"));
        double d1 = 0.0D;
        double d2 = 0.0D;
        ////
        int k;
        ////
        label35: while (true) {
            if (i < j) {
                k = 0;
                int i1 = 0;
                int i2;
                for (i2 = i;; i2 = i) {
                    i = i2 + 1;
                    i2 = arrayOfByte[i2] - 63;
                    i1 |= (i2 & 0x1F) << k;
                    k += 5;
                    if (i2 < 32) {
                        if ((i1 & 0x1) != 0) {
                            k = i1 >> 1 ^ 0xFFFFFFFF;
                            break;
                        }
                        k = i1 >> 1;
                        break;
                    }
                }
            } else {
                return arrayList;
            }
            d2 += k;
            int n = 0;
            int m = 0;
//            int k;
            for (k = i;; k = i) {
                i = k + 1;
                k = arrayOfByte[k] - 63;
                m |= (k & 0x1F) << n;
                n += 5;
                if (k < 32) {
                    if ((m & 0x1) != 0) {
                        k = m >> 1 ^ 0xFFFFFFFF;
                    } else {
                        k = m >> 1;
                    }
                    double d = d1 + k;
                    if (d > 9.0E7D || d < -9.0E7D) {
                        d1 = 1.0E7D;
                    } else {
                        d1 = 1000000.0D;
                    }
                    arrayList.add(new PolyPoint(d / d1, d2 / d1));
                    d1 = d;
                    continue label35;
                }
            }
//            break;
        }
    }

//    private static boolean parseHeadLine(String paramString, WMInteractiveKey paramWMInteractiveKey) {
//        boolean bool;
//        Scanner scanner = (new Scanner(paramString)).useDelimiter("\\|");
//        scanner.next();
//        int i = scanner.nextInt();
//        scanner.nextInt();
//        if (i == 0) {
//            bool = true;
//        } else {
//            bool = false;
//        }
//        if (bool)
//            WMTileManager.get().addFinalTileKey((WMBaseKey)paramWMInteractiveKey);
//        return bool;
//    }

//    @Nullable
//    private static WMInteractiveTileObject parseNextLine(String paramString) {
//        Map<Integer, String> map;
//        new WMMinBoundsRect();
//        HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>();
//        PolygonStyle polygonStyle = PolygonStyle.WIKIMAPIA_STYLE;
//        String str1 = null;
//        int j = 0;
//        StringBuilder stringBuilder = new StringBuilder();
//        Scanner scanner = (new Scanner(paramString)).useDelimiter("\\|");
//        String str3 = scanner.next();
//        int k = Integer.parseInt(scanner.next());
//        WMMinBoundsRect wMMinBoundsRect = new WMMinBoundsRect(scanner.next());
//        scanner.next();
//        scanner.next();
//        String str4 = scanner.next();
//        paramString = str1;
//        int i = j;
//        HashMap<Object, Object> hashMap1 = hashMap2;
//        if (str4 != null) {
//            paramString = str1;
//            i = j;
//            hashMap1 = hashMap2;
//            if (str4.length() > 0) {
//                Map<Integer, String> map1 = parseTitles(str4, null);
//                paramString = str1;
//                i = j;
//                map = map1;
//                if (!false) {
//                    Iterator<Integer> iterator = map1.keySet().iterator();
//                    paramString = str1;
//                    i = j;
//                    map = map1;
//                    if (iterator.hasNext()) {
//                        Integer integer = iterator.next();
//                        paramString = map1.get(integer);
//                        i = integer.intValue();
//                        map = map1;
//                    }
//                }
//            }
//        }
//        if (PolygonStyle.values()[Integer.parseInt(scanner.next())].equals(PolygonStyle.WIKIMAPIA_STYLE))
//            return null;
//        stringBuilder.append(scanner.next());
//        while (scanner.hasNext()) {
//            stringBuilder.append("|");
//            stringBuilder.append(scanner.next());
//        }
//        String str2 = stringBuilder.toString();
//        str1 = paramString;
//        j = i;
//        if (map.size() > 1) {
//            int m = WikiApp.getInstance().getResources().getInteger(2131361795);
//            str1 = paramString;
//            j = i;
//            if (m != i) {
//                Iterator<Integer> iterator = map.keySet().iterator();
//                while (true) {
//                    str1 = paramString;
//                    j = i;
//                    if (iterator.hasNext()) {
//                        Integer integer = iterator.next();
//                        if (integer.intValue() == m) {
//                            str1 = map.get(integer);
//                            j = m;
//                            break;
//                        }
//                        continue;
//                    }
//                    break;
//                }
//            }
//        }
//        return WMInteractiveTileObject.initWithObjectUid(str3, k, wMMinBoundsRect, str2, str1, j);
//    }
//
//    public static WMInteractiveTile parseTileObjects(WMInteractiveKey paramWMInteractiveKey) throws Exception {
//        File file = new File(paramWMInteractiveKey.getLocalPath());
//        boolean bool3 = false;
//        boolean bool1 = false;
//        LinkedList<WMInteractiveTileObject> linkedList = new LinkedList();
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//        boolean bool2 = bool3;
//        try {
//            String str = bufferedReader.readLine();
//            bool2 = bool3;
//            if (!TextUtils.isEmpty(str)) {
//                bool2 = bool3;
//                bool1 = parseHeadLine(str, paramWMInteractiveKey);
//            }
//            while (true) {
//                bool2 = bool1;
//                str = bufferedReader.readLine();
//                if (str != null) {
//                    bool2 = bool1;
//                    if (!TextUtils.isEmpty(str)) {
//                        bool2 = bool1;
//                        linkedList.add(parseNextLine(str));
//                    }
//                    continue;
//                }
//                return new WMInteractiveTile((WMBaseKey)paramWMInteractiveKey, bool1, linkedList);
//            }
//        } catch (Exception exception) {
//            logger.error(exception.toString(), exception);
//            bufferedReader.close();
//            return new WMInteractiveTile((WMBaseKey)paramWMInteractiveKey, bool1, linkedList);
//        } finally {
//            bufferedReader.close();
//        }
//    }

    //Разделитель полей в юникоде (U+001F)
    private static String SEGMENT_SEPARATOR ="\\x1f";

    private static Map<Integer, String> parseTitles(String paramString1, String paramString2) {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (String str : paramString1.split("\\x1f")) {
            byte b = str.getBytes()[0];
            str = str.substring(1, str.length());
            if (str != null)
                hashMap.put(Integer.valueOf(b - 32), str);
        }
        return (Map)hashMap;
    }

//    enum PolygonStyle {
//        GOOGLE_STYLE, WIKIMAPIA_STYLE;
//
//        static {
//            $VALUES = new PolygonStyle[] { WIKIMAPIA_STYLE, GOOGLE_STYLE };
//        }
//    }
}
