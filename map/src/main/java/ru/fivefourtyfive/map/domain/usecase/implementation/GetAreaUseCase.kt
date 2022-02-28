package ru.fivefourtyfive.map.domain.usecase.implementation

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import ru.fivefourtyfive.map.domain.usecase.abstraction.IGetAreaUseCase
import ru.fivefourtyfive.map.presentation.viewmodel.MapViewState
import ru.fivefourtyfive.places.domain.datastate.AreaDataState
import ru.fivefourtyfive.map.domain.repository.abstratcion.IMapRepository
import ru.fivefourtyfive.map.presentation.ui.overlay.PlacePolygon
import ru.fivefourtyfive.map.presentation.util.toPlacePolygon
import ru.fivefourtyfive.places.framework.presentation.abstraction.IReducer
import javax.inject.Inject

class GetAreaUseCase @Inject constructor(
    private val repository: IMapRepository
) : IGetAreaUseCase {
    override var lonMin: Double = 0.0
    override var latMin: Double = 0.0
    override var lonMax: Double = 0.0
    override var latMax: Double = 0.0
    override var category: String? = null
    override var page: Int? = 1
    override var count: Int? = 100
    override var language: String? = null

    override suspend fun execute() = repository.getArea(
        lonMin,
        latMin,
        lonMax,
        latMax,
        category,
        page,
        count,
        language
    )
}