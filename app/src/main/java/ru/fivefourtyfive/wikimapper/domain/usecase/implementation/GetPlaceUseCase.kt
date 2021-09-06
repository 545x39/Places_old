package ru.fivefourtyfive.wikimapper.domain.usecase.implementation

import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.UseCase

class GetPlaceUseCase(override var request: GetPlaceUseCaseRequest?) : UseCase<GetPlaceUseCase.GetPlaceUseCaseRequest, GetPlaceUseCase.GetPlaceUseCaseResponse> {

    data class GetPlaceUseCaseRequest(val id: Int): UseCase.Request

    class GetPlaceUseCaseResponse: UseCase.Response

    override suspend fun execute() {
        //TODO
    }
}