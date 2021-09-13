package ru.fivefourtyfive.wikimapper.domain.datastate

import ru.fivefourtyfive.wikimapper.domain.entity.Area

sealed class AreaDataState : DataState {
    class Success(val area: Area) : AreaDataState()
    object Loading : AreaDataState()
    class Error(val message: String? = null) : AreaDataState()
}