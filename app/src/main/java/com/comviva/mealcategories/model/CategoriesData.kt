package com.comviva.mealcategories.model

import com.google.gson.annotations.SerializedName

data class Category(
        @SerializedName("strCategory")
        val categoryName: String?,
        @SerializedName("strCategoryDescription")
        val categoryDescription: String?,
        @SerializedName("strCategoryThumb")
        val categoryPic: String?)