package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.dto.PlaceDescriptionDTO
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.datasource.ILocalDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase.IUseCase
import javax.inject.Inject

class GetPlaceFromCacheUseCase @Inject constructor(private val dataSource: ILocalDataSource) :
    IUseCase<PlaceDetailsDataState> {

    private var id = 0

    override suspend fun execute() = runCatching {
        return@runCatching dataSource.getPlace(id)?.let {
            PlaceDetailsDataState.Success(PlaceDescriptionDTO(it))
        } ?: PlaceDetailsDataState.Error()
    }.getOrDefault(PlaceDetailsDataState.Error())


    inner class Builder {

        fun id(id: Int) = this.apply { this@GetPlaceFromCacheUseCase.id = id }

        fun build() = this@GetPlaceFromCacheUseCase
    }
}