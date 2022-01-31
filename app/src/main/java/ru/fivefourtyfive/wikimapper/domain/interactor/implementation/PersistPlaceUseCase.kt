package ru.fivefourtyfive.wikimapper.domain.interactor.implementation

import ru.fivefourtyfive.wikimapper.domain.datastate.PlaceDetailsDataState
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.ILocalDataSource
import ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.IUseCase
import javax.inject.Inject

class PersistPlaceUseCase @Inject constructor(private val localDataSource: ILocalDataSource) :
    IUseCase<Unit> {

    private var place: Place? = null

    override suspend fun execute() {
        place?.let { localDataSource.persistPlace(it) }
    }

    inner class Builder {

        fun place(state: PlaceDetailsDataState) = this.apply {
//            this@PersistPlaceUseCase.place = when (state is PlaceDetailsDataState.Success) {
//                true -> this@PersistPlaceUseCase.place = state.place
//                false -> null
//            }
        }

        fun build() = this@PersistPlaceUseCase
    }
}