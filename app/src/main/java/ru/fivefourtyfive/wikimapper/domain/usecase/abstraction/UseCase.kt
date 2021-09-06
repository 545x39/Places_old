package ru.fivefourtyfive.wikimapper.domain.usecase.abstraction

interface UseCase<REQ : UseCase.Request
        , RES : UseCase.Response> {

    suspend fun execute()

    /** It's nullable in case a concrete use case doesn't require passing in any parameters. */
    var request: REQ?

    /** Input boundary. */
    interface Request

    /** Repository boundary. */
    interface Response

    /** Output boundary. */
    interface Callback
}