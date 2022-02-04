package ru.fivefourtyfive.places.framework.presentation.abstraction

import ru.fivefourtyfive.places.domain.datastate.DataState

interface Reducer<DATA_STATE: DataState, VIEW_STATE: ViewState> {
    fun reduce(dataState: DATA_STATE): VIEW_STATE
}