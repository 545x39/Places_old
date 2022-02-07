package ru.fivefourtyfive.places.domain.usecase.abstraction

interface IUseCase<T> {
    suspend fun execute(): T
}