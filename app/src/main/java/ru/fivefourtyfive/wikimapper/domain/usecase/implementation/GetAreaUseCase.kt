package ru.fivefourtyfive.wikimapper.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import ru.fivefourtyfive.wikimapper.domain.datastate.AreaDataState
import ru.fivefourtyfive.wikimapper.domain.repository.abstraction.IAreaRepository
import ru.fivefourtyfive.wikimapper.domain.usecase.abstraction.IGetAreaUseCase
import javax.inject.Inject

class GetAreaUseCase @Inject constructor(
    private val repository: IAreaRepository
) : IGetAreaUseCase {
    override var lonMin: Double = 0.0
    override var latMin: Double = 0.0
    override var lonMax: Double = 0.0
    override var latMax: Double = 0.0
    override var category: String? = null
    override var page: Int? = 1
    override var count: Int? = 100
    override var language: String? = null

    override suspend fun execute(): Flow<AreaDataState> = runCatching {
        return@runCatching repository.getArea(
            lonMin,
            latMin,
            lonMax,
            latMax,
            category,
            page,
            count,
            language
        )
    }.getOrDefault(flowOf(AreaDataState.Error(null)).flowOn(IO))
}