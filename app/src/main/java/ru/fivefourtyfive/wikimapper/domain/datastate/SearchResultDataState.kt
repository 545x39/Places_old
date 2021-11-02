package ru.fivefourtyfive.wikimapper.domain.datastate

import ru.fivefourtyfive.wikimapper.domain.dto.SearchResultsDTO

sealed class SearchResultDataState : DataState{
    class Success(val area: SearchResultsDTO) : SearchResultDataState()
    object Loading : SearchResultDataState()
    class Error(val message: String? = null) : SearchResultDataState()
}
