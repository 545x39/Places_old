package ru.fivefourtyfive.places.di.module

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fivefourtyfive.places.BuildConfig
import ru.fivefourtyfive.places.framework.datasource.implementation.remote.Api
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

        fun okHttp(): OkHttpClient = (OkHttpClient.Builder()).apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor()
                .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
            )
        }.build()

        return Retrofit.Builder().apply {
            baseUrl(API_URL)
            client(okHttp())
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build()
    }

    @Singleton
    @Provides
    fun provideBaseApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}