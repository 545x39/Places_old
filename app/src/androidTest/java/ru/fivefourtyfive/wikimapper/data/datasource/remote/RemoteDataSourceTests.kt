package ru.fivefourtyfive.wikimapper.data.datasource.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.runner.RunWith
import ru.fivefourtyfive.wikimapper.BaseTest
import ru.fivefourtyfive.wikimapper.data.datasource.implementation.remote.Api
import ru.fivefourtyfive.wikimapper.di.TestAppComponent
import javax.inject.Inject

/*
    TEST CASES:
    1. Проверить запрос описания места со всеми возможными комбинациями опций.
    2. Проверить запрос полигонов по bounding box с разными языками.
    3. Проверить поведение при отсутствии сети
 */

@RunWith(AndroidJUnit4::class)
class RemoteDataSourceTests : BaseTest() {

    @Inject
    lateinit var api: Api

    init {
        (application.appComponent as TestAppComponent).inject(this)
    }

    @Before
    fun checkInit(){
        assertTrue((::api.isInitialized))
    }

}