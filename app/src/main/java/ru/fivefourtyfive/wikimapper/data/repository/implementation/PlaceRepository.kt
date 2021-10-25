package ru.fivefourtyfive.wikimapper.data.repository.implementation

import ru.fivefourtyfive.wikimapper.domain.interactor.GetPlace
import javax.inject.Inject

class PlaceRepository @Inject constructor(private val getPlace: GetPlace) {

    suspend fun getPlace(id: Int, dataBlocks: String? = null) = getPlace.execute(id, dataBlocks)

}