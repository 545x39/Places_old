package ru.fivefourtyfive.wikimapper.domain.interactor.abstraction.usecase

interface IUseCase<T> {
    suspend fun execute(): T?
}