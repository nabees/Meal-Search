package com.comviva.mealcategories.di

import com.comviva.mealcategories.model.AppDataApi
import com.comviva.mealcategories.model.AppDataService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val baseUrl = "https://www.themealdb.com"

    @Provides
    fun provideCategoryService(): AppDataService {
        return AppDataService()
    }

    @Provides
    fun provideCategoriesApi(): AppDataApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AppDataApi::class.java)
    }
}