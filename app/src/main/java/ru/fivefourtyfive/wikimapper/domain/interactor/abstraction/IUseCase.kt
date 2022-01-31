package ru.fivefourtyfive.wikimapper.domain.interactor.abstraction

interface IUseCase<T> {
    suspend fun execute(): T
}