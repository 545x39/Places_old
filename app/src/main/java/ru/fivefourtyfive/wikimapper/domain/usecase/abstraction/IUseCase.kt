package ru.fivefourtyfive.wikimapper.domain.usecase.abstraction

interface IUseCase<T> {
    suspend fun execute(): T?
}