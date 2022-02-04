package ru.fivefourtyfive.places.di.module

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fivefourtyfive.places.BuildConfig
import ru.fivefourtyfive.places.framework.datasource.remote.Api
import ru.fivefourtyfive.places.util.Network.API_URL
import ru.fivefourtyfive.places.util.Network.CONNECT_TIMEOUT
import ru.fivefourtyfive.places.util.Network.READ_TIMEOUT
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        //<editor-fold defaultstate="collapsed" desc="INNER FUNCTIONS">
        /** Huge response may result in OutOfMemoryError.*/
        fun OkHttpClient.Builder.addLoggingInterceptor() {
            if (BuildConfig.DEBUG) {
                with(HttpLoggingInterceptor()) {
                    level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(this)
                }
            }
        }

        fun okHttp(): OkHttpClient {
            with(OkHttpClient.Builder()) {
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                retryOnConnectionFailure(true)
                addLoggingInterceptor()
                return build()
            }
        }

        fun gsonConverter() = GsonConverterFactory.create(GsonBuilder().setLenient().create())

        // </editor-fold>

        return Retrofit.Builder().apply {
            baseUrl(API_URL)
            client(okHttp())
            addConverterFactory(gsonConverter())
        }.build()
    }

    @Singleton
    @Provides
    fun provideBaseApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}