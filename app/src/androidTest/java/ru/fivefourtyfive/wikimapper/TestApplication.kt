package ru.fivefourtyfive.wikimapper

import ru.fivefourtyfive.wikimapper.di.module.AppModule

class TestApplication : Places() {

    override fun initAppComponent(){
//        appComponent = DaggerTestAppComponent.factory().create(AppModule(this)).also { it.inject(this) }
    }
}