package com.comviva.mealcategories.model

import com.google.gson.annotations.SerializedName

data class Meals(
        @SerializedName("meals")
        val meals:List<Meal>?)