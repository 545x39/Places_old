package ru.fivefourtyfive.map.presentation.viewmodel

import ru.fivefourtyfive.places.framework.presentation.abstraction.IEvent

@Suppress("SpellCheckingInspection")
sealed class MapEvent : IEvent {
    class GetAreaEvent(
        val north: Double,
        val east: Double,
        val south: Double,
        val west: Double
    ) : MapEvent()
    class SwitchMapModeEvent(val mode: Int): MapEvent()
    class SwitchWikimapiaOverlayEvent(val enable: Boolean): MapEvent()
    class SwitchTransportationOverlayEvent(val enable: Boolean): MapEvent()
    class SwitchFollowLocationEvent(val enable: Boolean): MapEvent()
    class SwitchCenterSelectionEvent(val enable: Boolean): MapEvent()
    class SwitchScaleEvent(val enable: Boolean): MapEvent()
    class SwitchGridEvent(val enable: Boolean): MapEvent()
}
