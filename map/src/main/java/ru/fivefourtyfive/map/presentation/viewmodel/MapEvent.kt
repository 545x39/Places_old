package ru.fivefourtyfive.map.presentation.viewmodel

import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Event

sealed class MapEvent : Event {
    class GetAreaEvent(
        val latMin: Double,
        val lonMin: Double,
        val latMax: Double,
        val lonMax: Double
    ) : MapEvent()
}
