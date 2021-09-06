package ru.fivefourtyfive.wikimapper

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.BuildConfig
import ru.fivefourtyfive.wikimapper.di.AppComponent
import ru.fivefourtyfive.wikimapper.di.DaggerAppComponent
import ru.fivefourtyfive.wikimapper.di.module.AppModule
import timber.log.Timber

class Wikimapper : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(AppModule(this)).apply {
            appComponent = this
            inject(this@Wikimapper)
        }
        setNightMode()
        initTimber()
        Timber.e("!!!!!!!!!!!!!!!!!!!!!!!!!!! INIT: " + ::appComponent.isInitialized)
    }

    private fun setNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                true -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                false -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }
        )
    }

//    private fun initTimber() {
//        if (BuildConfig.DEBUG) {
//            Timber.plant(object : Timber.DebugTree() {
//                override fun createStackElementTag(element: StackTraceElement): String? {
//                    return super.createStackElementTag(element)
//                        ?.plus(". CLS: [${element.className}], LN: [${element.lineNumber}], MTD: [${element.methodName}]")
//                }
//            })
//        }
//    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element)
                        ?.plus(": ${element.methodName}:${element.lineNumber}")
                }
            })
        }
    }
}