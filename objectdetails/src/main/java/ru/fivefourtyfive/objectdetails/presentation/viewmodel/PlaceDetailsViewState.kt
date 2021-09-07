package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.ViewState

/** Main place details UI blocks would be:
 * - Base info (Title, description, URL, also location and sometimes wiki link). This UI block is always present.
 * - Photos
 * - Comments
 * - Tags
 */
sealed class PlaceDetailsViewState : ViewState {
    class Success(val place: Place): PlaceDetailsViewState()
    class Error(val message: String? = null): PlaceDetailsViewState()
    object Loading: PlaceDetailsViewState()
}