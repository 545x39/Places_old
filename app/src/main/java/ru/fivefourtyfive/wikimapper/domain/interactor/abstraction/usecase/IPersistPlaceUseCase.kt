package ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase

import ru.fivefourtyfive.wikimapper.domain.entity.Place

interface IPersistPlaceUseCase: IUseCase<Unit> {

    var place: Place?

    fun withPlace(place: Place?) = this.apply { this.place = place }

}