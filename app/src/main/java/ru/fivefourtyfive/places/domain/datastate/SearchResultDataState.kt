package ru.fivefourtyfive.places.domain.datastate

import ru.fivefourtyfive.places.domain.dto.places.SearchResultsDTO

sealed class SearchResultDataState : DataState{
    class Success(val area: SearchResultsDTO) : SearchResultDataState()
    object Loading : SearchResultDataState()
    class Error(val message: String? = null) : SearchResultDataState()
}
