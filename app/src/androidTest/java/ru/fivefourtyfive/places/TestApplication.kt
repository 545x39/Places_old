package ru.fivefourtyfive.places

import ru.fivefourtyfive.places.di.DaggerTestAppComponent
import ru.fivefourtyfive.places.di.module.AppModule

class TestApplication : Places() {

    override fun initAppComponent(){
        appComponent = DaggerTestAppComponent.factory().create(AppModule(this)).also { it.inject(this) }
    }
}