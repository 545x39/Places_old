package ru.fivefourtyfive.wikimapper.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.fivefourtyfive.wikimapper.Places
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.local.Database
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.local.util.Path.DB_DIR
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.local.util.Path.DB_FILENAME
import java.io.File
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoom(app: Places) =
        Room.databaseBuilder(
            app,
            Database::class.java,
            "${app.filesDir}${File.separator}$DB_DIR${File.separator}$DB_FILENAME"
        ).fallbackToDestructiveMigration().build()
}