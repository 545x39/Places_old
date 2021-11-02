package ru.fivefourtyfive.wikimapper.presentation.ui.abstraction

import ru.fivefourtyfive.wikimapper.domain.datastate.DataState

interface Reducer<DATA_STATE: DataState, VIEW_STATE: ViewState> {
    fun reduce(dataState: DATA_STATE): VIEW_STATE
}