package com.comviva.mealcategories.model


import com.comviva.mealcategories.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class AppDataService {
    @Inject
    lateinit var api: AppDataApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCategories(): Single<Categories> {
        return api.getCategories()
    }

    fun getMeals(c: String): Single<Meals> {
        return api.getMeals(c)
    }
}