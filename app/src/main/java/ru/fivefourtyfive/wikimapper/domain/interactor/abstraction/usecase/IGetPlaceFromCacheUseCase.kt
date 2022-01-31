package ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase

import ru.fivefourtyfive.wikimapper.domain.entity.Place

interface IGetPlaceFromCacheUseCase : IUseCase<Place> {

    var id: Int?

    fun id(id: Int): IGetPlaceFromCacheUseCase = this.apply { this.id = id }
}