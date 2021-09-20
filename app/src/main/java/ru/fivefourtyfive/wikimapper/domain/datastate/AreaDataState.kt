package ru.fivefourtyfive.wikimapper.domain.datastate

import ru.fivefourtyfive.wikimapper.domain.dto.AreaDTO

sealed class AreaDataState : DataState {
    class Success(val area: AreaDTO) : AreaDataState()
    object Loading : AreaDataState()
    class Error(val message: String? = null) : AreaDataState()
}