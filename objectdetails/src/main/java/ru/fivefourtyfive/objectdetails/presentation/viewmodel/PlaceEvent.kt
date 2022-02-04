package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import ru.fivefourtyfive.places.framework.presentation.abstraction.Event

sealed class PlaceEvent : Event {
    class GetPlace(val id : Int): PlaceEvent()
    class SetSlideshow(val enable: Boolean): PlaceEvent()
//    class ShowOnMap(val lat : Double, val lon: Double): PlaceEvent()
//    class ShareCoordinates(context: Context, val lat : Double, val lon: Double): PlaceEvent()
//    class ShareLink(context: Context, link: String): PlaceEvent()
//    class ShowOnWebsite(context: Context, link: String): PlaceEvent()
//    class ShowOnWikipedia(context: Context, link: String): PlaceEvent()
}