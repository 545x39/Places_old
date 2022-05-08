package ru.fivefourtyfive.tracker.di.module

import dagger.Binds
import dagger.Module
import ru.fivefourtyfive.tracker.data.datasource.abstraction.ILocationDataSource
import ru.fivefourtyfive.tracker.data.repository.implementation.TrackRepository
import ru.fivefourtyfive.tracker.di.TrackerScope
import ru.fivefourtyfive.tracker.domain.repository.abstraction.ITrackRepository
import ru.fivefourtyfive.tracker.domain.usecase.abstraction.factory.ITrackerUseCaseFactory
import ru.fivefourtyfive.tracker.domain.usecase.implementation.factory.TrackerUseCaseFactory
import ru.fivefourtyfive.tracker.framework.datasource.implementation.LostLocationDataSource

@Module
interface TrackerModule {

    @TrackerScope
    @Binds
    fun provideTrackRepository(repository: TrackRepository): ITrackRepository

    @TrackerScope
    @Binds
    fun provideTrackUseCaseFactory(factory: TrackerUseCaseFactory) : ITrackerUseCaseFactory

}