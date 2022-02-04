package ru.fivefourtyfive.places.domain.datastate

import ru.fivefourtyfive.places.domain.entity.dto.AreaDTO

sealed class AreaDataState : DataState {
    class Success(val area: AreaDTO) : AreaDataState()
    object Loading : AreaDataState()
    class Error(val message: String? = null) : AreaDataState()
}