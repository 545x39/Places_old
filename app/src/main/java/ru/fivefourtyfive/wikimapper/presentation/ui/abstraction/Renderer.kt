package ru.fivefourtyfive.wikimapper.presentation.ui.abstraction

interface Renderer<VIEW_STATE : ViewState> {
    fun render(viewState: VIEW_STATE)
}