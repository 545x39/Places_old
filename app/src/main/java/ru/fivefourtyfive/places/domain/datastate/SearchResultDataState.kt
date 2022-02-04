package ru.fivefourtyfive.places.domain.datastate

import ru.fivefourtyfive.places.domain.entity.dto.SearchResultsDTO

sealed class SearchResultDataState : DataState{
    class Success(val area: SearchResultsDTO) : SearchResultDataState()
    object Loading : SearchResultDataState()
    class Error(val message: String? = null) : SearchResultDataState()
}
