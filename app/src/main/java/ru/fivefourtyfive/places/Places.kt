package ru.fivefourtyfive.places

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import ru.fivefourtyfive.places.di.AppComponent
import ru.fivefourtyfive.places.di.DaggerAppComponent
import ru.fivefourtyfive.places.di.module.AppModule
import ru.fivefourtyfive.places.util.createNotificationChannels
import timber.log.Timber

open class Places : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initAppComponent()
        setNightMode()
        createNotificationChannels(this)
    }

    protected open fun initAppComponent() {
        appComponent = DaggerAppComponent.factory().create(AppModule(this)).also { it.inject(this) }
    }

    private fun setNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                true -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                false -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }
        )
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "[CLS:%s], [MTD:%s], [LN:%s]",
                        super.createStackElementTag(element),
                        element.methodName,
                        element.lineNumber
                    )
                }
            })
        }
    }
}