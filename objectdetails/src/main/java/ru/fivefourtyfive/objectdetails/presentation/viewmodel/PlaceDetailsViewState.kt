package ru.fivefourtyfive.objectdetails.presentation.viewmodel

import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.presentation.ui.abstraction.ViewState

/** Main place details UI blocks would be:
 * - Base info (Title, description, URL, also location and sometimes wiki link). This UI block is always present.
 * - Photos
 * - Comments
 * - Tags
 * Hence the states.
 */
sealed class PlaceDetailsViewState : ViewState {
    class All(val place: Place): PlaceDetailsViewState()
    class NoPhotos(val place: Place): PlaceDetailsViewState()
    class NoComments(val place: Place): PlaceDetailsViewState()
    class NoTags(val place: Place): PlaceDetailsViewState()
    class NoPhotosAndTags(val place: Place): PlaceDetailsViewState()
    class NoPhotosAndComments(val place: Place): PlaceDetailsViewState()
    class NoCommentsAndTags(val place: Place): PlaceDetailsViewState()
    class Error(val message: String? = null): PlaceDetailsViewState()
    object Loading: PlaceDetailsViewState()
}