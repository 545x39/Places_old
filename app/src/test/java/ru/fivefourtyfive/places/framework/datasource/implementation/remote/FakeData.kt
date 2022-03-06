package ru.fivefourtyfive.places.framework.datasource.implementation.remote

import ru.fivefourtyfive.places.framework.datasource.implementation.remote.util.Value.RU
import ru.fivefourtyfive.places.domain.entity.*
import ru.fivefourtyfive.places.domain.entity.places.*

object FakeData {

    /** https://api.wikimapia.org/?key=example&function=place.getbyid&id=18307319&format=json&=&language=ru&data_blocks=main,comments,photos,location,geometry */
    fun getFort() = Place(
        id = 18307319,
        languageIso = RU,
        objectType = 1,
        languageId = 1,
        languageName = "Russian",
        title = "Форт \"Красная Горка\"",
        description = "Форт «Красная Горка» (Алексеевский) (в 1919 году переименован в «Краснофлотский») — один из двух мощных береговых фортов Кронштадтской позиции Морской крепости Петра Великого.\nВходил в состав мощной минно-артиллерийской позиции, надёжно защищавшей подступы к столице государства от подхода крупных кораблей противника. Нарушить эту систему обороны удалось только британским торпедным катерам, атаковавшим корабли, стоявшие в 1919 году на рейде Кронштадта. Этот эпизод явился одной из причин категорического отказа моряков-балтийцев выполнить указания большевистского руководства об уничтожении флота, что переросло вскоре в Кронштадтское восстание под лозунгом «Советы без большевиков».\nСтроительство под руководством генерал-майора Аполлона Алексеевича Шишкина было начато в 1909 году одновременно с фортом Ино (Николаевским) и закончено к 1915 году. Назван по деревне Красная Горка (ныне поселок Красная Горка), рядом с которой находится.\nФорт вошел в строй в начале 1914 года. К началу первой мировой войны гарнизон форта был укомплектован по штатам военного времени (2000 артиллеристов, 2000 пехотинцев и более 500 военнослужащих других родов войск). В боевых действиях первой мировой войны форт участия не принимал.\nК январю 1917 года гарнизон форта возрос до 5500 человек. В 1918 году в связи с близостью германской армии форт был заминирован. Взрыв был отменен, но заряды не были убраны. 19 августа во время сильной грозы несколько зарядов сдетонировало. На воздух взлетели пороховые погреба трех орудий Канэ и четырех 254-мм орудий. Артиллерийская прислуга погибла, орудия были сильно повреждены, а одно 254-мм орудие уничтожено.\nВо время Гражданской войны форт входил в систему оборонительных укреплений «красного» Петрограда. К этому времени на форте имелось 25 орудий калибра от 76 до 305 мм. Во время боевых действий форт открывал огонь по противнику: 20 ноября 1918 года по захваченной финнами батарее «Пуммола», ранее входившей в систему укрепления форта «Ино», 29 июня 1919 года по транспортному судну противника.\n13 июня 1919 года, во время наступления на Петроград Северного корпуса генерала А.П. Родзянко, гарнизон форта поднял антибольшевистское восстание, которое было подавлено огнём и десантом с кораблей Балтийского флота (по форту было выпущено более 600 12\" снарядов). Восставшие оборонялись, обстреливая Кронштадт и корабли красного Балтийского флота. 16 июня красноармейцы вошли в оставленный восставшими форт. Белая армия не смогла воспользоваться переходом форта на сторону белых, т. к. белые узнали о восстании только на третий день — день, когда восстание было ликвидировано.\n30 октября 1919 года форт вступил в артиллерийскую дуэль с английским монитором «Эребус» (англ. HMS Erebus), выполнявшим робкую и запоздалую огневую поддержку Британским флотом наступления армии Юденича.\nВ 1921 году орудия «Красной горки» вели огонь по Кронштадту, подавляя антибольшевисткое восстание.\n30 ноября 1939 года в 8 часов 3 минуты утра форт в числе других батарей Кронштадта открыл огонь по финским укреплениям «Линии Маннергейма». Однако без корректировки огонь был неэффективен, и вскоре стрельба была прекращена.\nВо время Великой Отечественной войны форт стал центром обороны Ораниенбаумского плацдарма. Радиус действия его орудий фактически определял дальность расположения немецких позиций. Вокруг форта были проложены железнодорожные пути, по которым на специальных транспортерах перемещались крупнокалиберные корабельные орудия (356-, 305-, 180-мм).\nПосле войны форт некоторое время поддерживался в боеготовом состоянии, потом его помещения использовали в качестве складов, а затем почти полностью забросили.\nВ 2007 году совместо с муниципальными властями Лебяжьего, Ломоносовского района и Ленинградской области военные моряки решили в ближайшее время создать музей на месте легендарного форта.\nВесной 2012 года муниципальными властями Лебяжьего, Ломоносовского района и Ленинградской области взят курс на уничтожение этого шедевра русской фортификации, а также исключения его из списка культурных объектов Всемирного наследия ЮНЕСКО.\nБатарея 8×11\" гаубиц\nБатарея 8×10\" орудий\nБатарея 6×6\" орудий Канэ\nБатарея 4×12\" орудия в открытых установках\nБатарея 4×12\" орудия в двухорудийных башнях\n\nФорт является одним из культурных объектов Всемирного наследия ЮНЕСКО с 1990 года.\n\nhttp://www.lenww2.ru/index.php/region01/area11?id=1074\nhttp://www.krasnaya-gorka.sbor.ru/fort/fort.htm\n\nКак это уничтожается и разворовывается в наше время.\n Текст и ссылки на варварские действия по объекту. В основном касается государственных органов.\nhttps://vitjko.livejournal.com/63191.html",
        wikipedia = "http://ru.wikipedia.org/wiki/Красная_Горка_(форт)",
        isBuilding = false,
        isRegion = false,
        isDeleted = false,
//        tags = listOf(
//            Tag(196, "Вторая мировая война"),
//            Tag(1147, "Первая мировая война"),
//            Tag(2750, "оборонительное сооружение"),
//            Tag(6413, "артиллерийская батарея"),
//            Tag(44858, "объект всемирного наследия ЮНЕСКО")
//        ),
//        photos = listOf(
//            Photo(
//                id = 1449250,
//                size = 105297,
//                status = 0,
//                objectId = 18307319,
//                userId = 799076,
//                userName = "miraru1",
//                time = 1292502478,
//                timeString = "11 лет назад",
//                lastUserId = 799076,
//                lastUserName = "miraru1",
//                url960 = "http://photos.wikimapia.org/p/00/01/44/92/50_960.jpg",
//                url1280 = "http://photos.wikimapia.org/p/00/01/44/92/50_1280.jpg",
//                urlBig = "http://photos.wikimapia.org/p/00/01/44/92/50_big.jpg",
//                thumbnailUrl = "http://photos.wikimapia.org/p/00/01/44/92/50_75.jpg",
//                thumbnailRetinaUrl = "http://photos.wikimapia.org/p/00/01/44/92/50_75@2x.jpg"
//            ),
//            Photo(
//                id = 2560539,
//                size = 4247643,
//                status = 0,
//                objectId = 18307319,
//                userId = 0,
//                userName = "Guest",
//                time = 1341073199,
//                timeString = "9 лет назад",
//                lastUserId = 0,
//                lastUserName = "Guest",
//                userIp = "0",
//                fullUrl = "http://photos.wikimapia.org/p/00/02/56/05/39_full.jpg",
//                url960 = "http://photos.wikimapia.org/p/00/02/56/05/39_960.jpg",
//                url1280 = "http://photos.wikimapia.org/p/00/02/56/05/39_1280.jpg",
//                urlBig = "http://photos.wikimapia.org/p/00/02/56/05/39_big.jpg",
//                thumbnailUrl = "http://photos.wikimapia.org/p/00/02/56/05/39_75.jpg",
//                thumbnailRetinaUrl = "http://photos.wikimapia.org/p/00/02/56/05/39_75@2x.jpg"
//            )
//        ),
//        comments = listOf(
//            Comment(
//                placeId = 18307319,
//                num = 1,
//                langId = 1,
//                userId = 0,
//                userIp = -1293120350,
//                userPhoto = "",
//                name = "fcts2009",
//                message = "Фото устарели,сейчас нет колючки,орудия покрашены,доступ открыт!Отличное место для любителей истории,не хуже бункеров линии Мажино или Атлантического Вала Гитлера!Проблема только добраться,дорог нет,распутица...",
//                good = 0,
//                bad = 1,
//                block = false,
//                date = 1411164683,
//                moderUid = 0,
//                moderatorName = null,
//                isDeleted = false
//            ),
//            Comment(
//                placeId = 18307319,
//                num = 2,
//                langId = 1,
//                userId = 2363734,
//                userIp = -720032043,
//                userPhoto = "/img/nofoto_50.png",
//                name = "FotosergS",
//                message = "Форт \"Красная Горка\" (подробный фотоальбом): \r\nhttps://fotki.yandex.ru/users/fotosergs/album/140144/",
//                good = 0,
//                bad = 0,
//                block = false,
//                date = 1440790244,
//                moderUid = 0,
//                moderatorName = null,
//                isDeleted = false
//            )
//        ),
        parentId = 0,
        x = 293234754, // /10_000_000
        y = 599717006,
        square = 21222.0,
        polygon = listOf(
            PolygonPoint(29.2981339, 59.9720664),
            PolygonPoint(29.3089485, 59.9698758),
            PolygonPoint(29.3146992, 59.9702195),
            PolygonPoint(29.3197632, 59.9656231),
            PolygonPoint(29.3236256, 59.9651075),
            PolygonPoint(29.329977, 59.9648927),
            PolygonPoint(29.3455124, 59.9720664),
            PolygonPoint(29.3488169, 59.9746864),
            PolygonPoint(29.3459845, 59.9754809),
            PolygonPoint(29.3424654, 59.9765975),
            PolygonPoint(29.3416929, 59.9771129),
            PolygonPoint(29.3395472, 59.9775852),
            PolygonPoint(29.3371868, 59.9775208),
            PolygonPoint(29.3361568, 59.9775852),
            PolygonPoint(29.3349552, 59.9780362),
            PolygonPoint(29.3334532, 59.9781435),
            PolygonPoint(29.3318653, 59.9780147),
            PolygonPoint(29.3297625, 59.9778429),
            PolygonPoint(29.3276596, 59.9775208),
            PolygonPoint(29.3253851, 59.9772846),
            PolygonPoint(29.3225098, 59.9768981),
            PolygonPoint(29.3185616, 59.9764472),
            PolygonPoint(29.3123817, 59.9762539),
            PolygonPoint(29.310708, 59.9764687),
            PolygonPoint(29.3078756, 59.977027),
            PolygonPoint(29.3057728, 59.9776711),
            PolygonPoint(29.3037987, 59.9785085)
        ),
        distance = 5000
    )

    /** https://api.wikimapia.org/?key=example&function=place.getbyid&id=11867376&format=json&=&language=ru&data_blocks=main,comments,photos,location,geometry */
    fun getDefenceLine() = Place(
        id = 11867376,
        objectType = 1,
        languageId = 1,
        languageIso = RU,
        languageName = "Russian",
        title = "Сухопутная оборона форта \"Красная Горка\"",
        description = "Внешний обвод сухопутной обороны форта \\\"Красная Горка\\\" (\\\"Алексеевский\\\", \\\"Краснофлотский\\\"). Состоит из:\\n- 15 артиллерийских позиций с для выкатных 3-дм/20 противоштурмовых пушек обр. 1903 г. с укрытиями и погребами боезапаса для пушек и 50 чел. л/с;\\n- 4 пулеметных гнезд на два пулемета с укрытиями и погребами боезапаса для пулеметов и 30 чел. л/с;\\n- 1 четырехпулеметного капонира с укрытием для личного состава;\\n- 4 сборных пулеметных железобетонных огневых точек, так называемых тяжелых пулеметных гнезд системы Колотовкина (Колотовского) (установленных позднее, в конце 1920-х гг.).\\n\\nВсе сооружения, кроме ЖБОТов построены в 1910-15 гг.\\n\\nУчастки между артиллерийскими и пулеметными позициями частично перекрыты бетонированными окопами с брустверами из бетона и брони (броня не сохранилась), частично — окопами полного профиля с валами. Пространство перед линией обороны было защищено рвом с эскарпом и 4-6 рядами колючей проволоки на железных кольях-надолбах (в основном, утрачены). Болото перед фортом при необходимости могло быть подтоплено на высоту ок. 3 м, для чего имеется шлюз затопления.\\n\\nВ тылу расположены 5 казарм-убежищ на 120 чел. с брустверами для стрелков.\\n\\nhttp://www.lenww2.ru/index.php/region01/area11?id=1074\\nhttp://www.krasnaya-gorka.sbor.ru/index.html",
        wikipedia = "",
        isBuilding = false,
        isRegion = false,
        isDeleted = false,
//        tags = listOf(
//            Tag(1147, "Первая мировая война"),
//            Tag(2750, "оборонительное сооружение"),
//        ),
        parentId = 0,
        x = 293363824,
        y = 599710642,
        square = 8952.9750083422,
//        photos = listOf(
//            Photo(
//                id = 1708706,
//                size = 205768,
//                status = 0,
//                objectId = 11867376,
//                userId = 0,
//                userName = "Guest",
//                time = 1306615916,
//                timeString = "10 лет назад",
//                lastUserId = 0,
//                lastUserName = "Guest",
//                userIp = "0",
//                url960 = "http://photos.wikimapia.org/p/00/01/70/87/06_960.jpg",
//                url1280 = "http://photos.wikimapia.org/p/00/01/70/87/06_1280.jpg",
//                urlBig = "http://photos.wikimapia.org/p/00/01/70/87/06_big.jpg",
//                thumbnailUrl = "http://photos.wikimapia.org/p/00/01/70/87/06_75.jpg",
//                thumbnailRetinaUrl = "http://photos.wikimapia.org/p/00/01/70/87/06_75@2x.jpg"
//            ),
//            Photo(
//                id = 1708707,
//                size = 225292,
//                status = 0,
//                objectId = 11867376,
//                userId = 0,
//                userName = "Guest",
//                time = 1306615949,
//                timeString = "10 лет назад",
//                lastUserId = 0,
//                lastUserName = "Guest",
//                userIp = "0",
//                url960 = "http://photos.wikimapia.org/p/00/01/70/87/07_960.jpg",
//                url1280 = "http://photos.wikimapia.org/p/00/01/70/87/07_1280.jpg",
//                urlBig = "http://photos.wikimapia.org/p/00/01/70/87/07_big.jpg",
//                thumbnailUrl = "http://photos.wikimapia.org/p/00/01/70/87/07_75.jpg",
//                thumbnailRetinaUrl = "http://photos.wikimapia.org/p/00/01/70/87/07_75@2x.jpg"
//            )
//        ),
//        comments = listOf(
//            Comment(
//                placeId = 11867376,
//                num = 1,
//                langId = 1,
//                userId = 56155,
//                userIp = 1547518165,
//                userPhoto = "/img/nofoto_50.png",
//                name = "ruspodplav",
//                message = "Эт сухопутная оборона форта.",
//                good = 4,
//                bad = 0,
//                block = false,
//                date = 1249158887,
//                moderUid = 0,
//                moderatorName = null,
//                isDeleted = false
//            ),
//            Comment(
//                placeId = 11867376,
//                num = 2,
//                langId = 1,
//                userId = 0,
//                userIp = -1304221335,
//                userPhoto = "",
//                name = "Glebr",
//                message = "Оборонительная линия впечатляет! \n",
//                good = 2,
//                bad = 0,
//                block = false,
//                date = 1294861729,
//                moderUid = 0,
//                moderatorName = null,
//                isDeleted = false
//            )
//        ),
        polygon = listOf(
            PolygonPoint(29.331758, 59.966697),
            PolygonPoint(29.329741, 59.9660204),
            PolygonPoint(29.3267369, 59.96645),
            PolygonPoint(29.3242693, 59.9678354),
            PolygonPoint(29.3238831, 59.9683294),
            PolygonPoint(29.3232608, 59.9689738),
            PolygonPoint(29.3212652, 59.9697255),
            PolygonPoint(29.3192592, 59.9710133),
            PolygonPoint(29.3189373, 59.9715502),
            PolygonPoint(29.3187654, 59.9721577),
            PolygonPoint(29.3178859, 59.9727206),
            PolygonPoint(29.3194631, 59.9733488),
            PolygonPoint(29.3190446, 59.9747231),
            PolygonPoint(29.3190125, 59.9767631),
            PolygonPoint(29.3172314, 59.976645),
            PolygonPoint(29.3162658, 59.9765806),
            PolygonPoint(29.3160084, 59.9752922),
            PolygonPoint(29.3160513, 59.9740896),
            PolygonPoint(29.3169954, 59.9731125),
            PolygonPoint(29.3169096, 59.9726078),
            PolygonPoint(29.3182829, 59.9704817),
            PolygonPoint(29.3182183, 59.9701121),
            PolygonPoint(29.3194628, 59.9691671),
            PolygonPoint(29.320836, 59.9677065),
            PolygonPoint(29.3235182, 59.9658056),
            PolygonPoint(29.3260717, 59.9653653),
            PolygonPoint(29.3314793, 59.965488),
            PolygonPoint(29.3354275, 59.9671956),
            PolygonPoint(29.3402341, 59.969075),
            PolygonPoint(29.343281, 59.9694293),
            PolygonPoint(29.3473795, 59.9702133),
            PolygonPoint(29.3516281, 59.970879),
            PolygonPoint(29.3541815, 59.9714804),
            PolygonPoint(29.3563488, 59.9722212),
            PolygonPoint(29.3567136, 59.9729085),
            PolygonPoint(29.3567564, 59.9730695),
            PolygonPoint(29.3511775, 59.9734132),
            PolygonPoint(29.3501475, 59.973295),
            PolygonPoint(29.3492248, 59.9723179),
            PolygonPoint(29.347358, 59.970922),
            PolygonPoint(29.3430665, 59.9705998),
            PolygonPoint(29.3391612, 59.9702133),
            PolygonPoint(29.3360069, 59.9691823),
            PolygonPoint(29.3337324, 59.9678614)
        ),
        distance = 5200
    )

    @Suppress("UNUSED_PARAMETER")
    fun getArea(
        lonMin: Double = 0.0,
        latMin: Double = 0.0,
        lonMax: Double = 0.0,
        latMax: Double = 0.0,
        category: String? = "",
        page: Int? = 1,
        count: Int? = 100,
        language: String? = RU
    ) = Places(
        places = listOf(getFort(), getDefenceLine()),
        version = "1.0",
        language = RU,
        page = 1,
        count = 2,
        found = 2
    )

    fun getSearchResults() = Places(
        places = listOf(getFort(), getDefenceLine()),
        version = "1.0",
        language = RU,
        page = 1,
        count = 2,
        found = 2
    )

    fun getCategories() = Categories(
        categories = listOf(
            Category(
                id = 516,
                amount = 221912,
                icon = "http://wikimapia.org/mapico/00/00/00/00/88.png",
                name = "военный объект"
            ),
            Category(
                id = 46085,
                amount = 32714,
                icon = "http://wikimapia.org/mapico/00/00/00/00/00.png",
                name = "военный памятник / мемориал"
            ),
            Category(
                id = 45239,
                amount = 7840,
                icon = "http://wikimapia.org/mapico/00/00/00/00/00.png",
                name = "исчезнувший военный объект"
            ),
            Category(
                id = 32,
                amount = 2922,
                icon = "http://wikimapia.org/mapico/00/00/00/00/00.png",
                name = "авиабаза/военный аэродром"
            ),
            Category(
                id = 9400,
                amount = 2581,
                icon = "http://wikimapia.org/mapico/00/00/00/00/00.png",
                name = "военный корабль"
            ),
            Category(
                id = 65440,
                amount = 2148,
                icon = "http://wikimapia.org/mapico/00/00/00/00/00.png",
                name = "военный радар"
            )
        ),
        page = 1,
        count = 2,
        found = 2
    )

    fun getCategory() = Category(
        id = 516,
        amount = 221912,
        icon = "http://wikimapia.org/mapico/00/00/00/00/88.png",
        name = "военный объект",
        description = "Вооружённые си́лы (ВС) — главная вооружённая организация государства или группы государств, предназначенная для обеспечения военной безопасности, защиты государственных интересов при агрессии и ведении войны, недопущения или ликвидации угрозы миру между государствами и безопасности. Кроме выполнения основных функций возложенных на вооружённые силы, они также могут привлекаться к поддержанию порядка в государстве при чрезвычайных ситуациях, ликвидации последствий природных и техногенных катастроф, а также для решения некоторых других государственных и международных задач.\nhttp://ru.wikipedia.org/wiki/%D0%92%D0%BE%D0%BE%D1%80%D1%83%D0%B6%D1%91%D0%BD%D0%BD%D1%8B%D0%B5_%D1%81%D0%B8%D0%BB%D1%8B",
        synonyms = listOf(Category(id = 102826, name = "вооружённые силы"))
    )

    fun getPlaceError() = Place(0).apply { debugInfo = DebugInfo(1, "ERROR: Place not found!") }

    fun onAreaError() = Places(debugInfo = DebugInfo(2, "ERROR: wrong area request"))

    fun onSearchError() = Places(debugInfo = DebugInfo(3, "ERROR: wrong search request"))

    fun onCategoryError() = Category(debugInfo = DebugInfo(4, "ERROR: wrong category request"))

    fun onCategoriesError() =
        Categories(debugInfo = DebugInfo(5, "ERROR: wrong categories request"))
}