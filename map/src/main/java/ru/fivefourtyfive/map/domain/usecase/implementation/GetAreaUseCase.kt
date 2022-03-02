package ru.fivefourtyfive.map.domain.usecase.implementation

import ru.fivefourtyfive.map.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapRepository
import javax.inject.Inject

class GetAreaUseCase @Inject constructor(
    private val repository: IMapRepository
) : IGetAreaUseCase {
    override var north: Double = 0.0
    override var west: Double = 0.0
    override var south: Double = 0.0
    override var east: Double = 0.0
    override var category: String? = null
    override var page: Int? = 1
    override var count: Int? = 100
    override var language: String? = null

    override suspend fun execute() = repository.getArea(
        north,
        west,
        south,
        east,
        category,
        page,
        count,
        language
    )
}