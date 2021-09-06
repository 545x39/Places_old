package ru.fivefourtyfive.wikimapper.data.repository.implementation

import ru.fivefourtyfive.wikimapper.data.repository.abstraction.RemoteDataSource
import ru.fivefourtyfive.wikimapper.domain.entity.Place
import javax.inject.Inject

class PlaceRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    fun getPlace(id: Int){
        remoteDataSource.getPlace(id)
    }

}