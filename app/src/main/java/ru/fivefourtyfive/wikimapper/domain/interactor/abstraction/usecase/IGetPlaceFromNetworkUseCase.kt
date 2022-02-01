package ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase

import ru.fivefourtyfive.wikimapper.domain.entity.Place

interface IGetPlaceFromNetworkUseCase : IUseCase<Place> {

    var id: Int?

    var dataBlocks: String?

    fun id(id: Int) = this.apply { this.id = id }

    fun dataBlocks(dataBlocks: String?) = this.apply { this.dataBlocks = dataBlocks }

}