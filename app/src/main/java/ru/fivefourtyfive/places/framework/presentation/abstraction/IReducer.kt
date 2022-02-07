package ru.fivefourtyfive.places.framework.presentation.abstraction

import ru.fivefourtyfive.places.domain.datastate.DataState

interface IReducer<DATA_STATE: DataState, VIEW_STATE: IViewState> {
    fun reduce(dataState: DATA_STATE): VIEW_STATE
}