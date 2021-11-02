package ru.fivefourtyfive.map.presentation.viewmodel

import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.Event

sealed class MapEvent : Event {
    class GetAreaEvent(
        val lonM: Double,
        val latM: Double,
        val lonMx: Double,
        val latMx: Double
    ) : MapEvent()
    class SwitchMapModeEvent(val mode: Int): MapEvent()
    class SwitchWikimapiaOverlayEvent(val enable: Boolean): MapEvent()
    class SwitchTransportationOverlayEvent(val enable: Boolean): MapEvent()
    class SwitchFollowLocationEvent(val enable: Boolean): MapEvent()
    class SwitchCenterSelectionEvent(val enable: Boolean): MapEvent()
    class SwitchScaleEvent(val enable: Boolean): MapEvent()
    class SwitchGridEvent(val enable: Boolean): MapEvent()
}
