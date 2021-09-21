package ru.fivefourtyfive.wikimapper

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import ru.fivefourtyfive.wikimapper.di.AppComponent
import ru.fivefourtyfive.wikimapper.di.DaggerAppComponent
import ru.fivefourtyfive.wikimapper.di.module.AppModule
import timber.log.Timber

class Places : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initTimber()
        DaggerAppComponent.factory().create(AppModule(this)).apply {
            appComponent = this
            inject(this@Places)
        }
        setNightMode()
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