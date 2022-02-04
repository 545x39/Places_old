package ru.fivefourtyfive.wikimapper.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.fivefourtyfive.wikimapper.framework.datasource.local.database.Database
import ru.fivefourtyfive.wikimapper.framework.datasource.local.database.util.Path.DB_DIR
import ru.fivefourtyfive.wikimapper.framework.datasource.local.database.util.Path.DB_FILENAME
import timber.log.Timber
import java.io.File
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoom(context: Context) =
        Room.databaseBuilder(
            context, Database::class.java,
            "${context.filesDir}${File.separator}$DB_DIR${File.separator}$DB_FILENAME"
        ).fallbackToDestructiveMigration().build()
            .also {
            Timber.e("DATABASE IS IN: ${context.filesDir}${File.separator}$DB_DIR${File.separator}$DB_FILENAME")
        }
}