package com.comviva.mealcategories.di

import com.comviva.mealcategories.model.AppDataService
import com.comviva.mealcategories.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: AppDataService)
    fun inject(viewModel:ListViewModel)
}