package com.test.series.retrofit.di

import android.app.Application
import android.content.Context
import com.test.series.repo.MainRepository
import com.test.series.retrofit.network.ApiService
import com.test.series.util.NetworkChecker

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Provides fun providesApiService(): ApiService =
        Retrofit.Builder()
            .run {
                baseUrl(ApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }.create(ApiService::class.java)




    @Provides @Singleton
    fun iD(seriesId:Int) :  Int {
        return  seriesId;
    }
}