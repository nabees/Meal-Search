package com.comviva.mealcategories.model

import com.google.gson.annotations.SerializedName

data class Meal(
        @SerializedName("strMeal")
        val mealName: String?,
        @SerializedName("strMealThumb")
        val mealPic: String?)