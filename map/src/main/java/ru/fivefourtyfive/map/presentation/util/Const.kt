package ru.fivefourtyfive.map.presentation.util

import org.osmdroid.views.CustomZoomButtonsController

const val MAP_LISTENER_DELAY = 600L

/** Smaller min values allows map to zoom out of to show more than one map repetition.*/
const val ZOOM_MIN = 2.0
const val ZOOM_MAX = 19.0
const val ZOOM_DEFAULT = 10.0
val ZOOM_CONTROLS_VISIBILITY = CustomZoomButtonsController.Visibility.NEVER