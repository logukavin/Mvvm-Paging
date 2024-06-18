package com.example.mvvm.module

import com.example.mvvm.api.AppApi
import com.example.mvvm.repository.AppRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiModule {


    @Provides
    @Singleton
    fun bindAppApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun bindsAppRepo(appApi: AppApi): AppRepo {
        return AppRepo(appApi)


    }
}