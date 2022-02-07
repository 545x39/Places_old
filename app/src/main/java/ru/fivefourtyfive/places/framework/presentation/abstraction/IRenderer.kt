package ru.fivefourtyfive.places.framework.presentation.abstraction

interface IRenderer<VIEW_STATE : IViewState> {
    fun render(viewState: VIEW_STATE)
}