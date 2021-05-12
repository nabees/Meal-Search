package com.comviva.mealcategories.model


import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AppDataApi {
    @GET("api/json/v1/1/categories.php")
    fun getCategories(): Single<Categories>

    @GET("api/json/v1/1/filter.php")
    fun getMeals(@Query("c") c: String?): Single<Meals>
}