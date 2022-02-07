package ru.fivefourtyfive.places.framework.presentation.abstraction

interface Renderer<VIEW_STATE : ViewState> {
    fun render(viewState: VIEW_STATE)
}