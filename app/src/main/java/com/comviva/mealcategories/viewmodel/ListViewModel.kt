package com.comviva.mealcategories.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.comviva.mealcategories.model.Category
import com.comviva.mealcategories.di.DaggerApiComponent
import com.comviva.mealcategories.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ListViewModel : ViewModel() {

    @Inject
    lateinit var appService: AppDataService

    init {
        DaggerApiComponent.create().inject(this)
    }
    private val disposable = CompositeDisposable()

    val meals = MutableLiveData<List<Meal>>()
    val mloadError = MutableLiveData<Boolean>()
    val mloading = MutableLiveData<Boolean>()
    lateinit var c: String

    val categories = MutableLiveData<List<Category>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCategories()
    }

    fun refreshMeals() {
        fetchMeals()
    }

    private fun fetchMeals() {
        mloading.value = true
        disposable.add(
                appService.getMeals(c)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Meals>() {
                            override fun onSuccess(value: Meals?) {
                                meals.value = value?.meals
                                mloadError.value = false
                                mloading.value = false
                            }

                            override fun onError(e: Throwable?) {
                                mloadError.value = true
                                mloading.value = false

                            }

                        })
        )
    }

    private fun fetchCategories() {
        loading.value = true
        disposable.add(
                appService.getCategories()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Categories>() {
                            override fun onSuccess(value: Categories?) {
                                categories.value = value?.categories
                                loadError.value = false
                                loading.value = false
                            }

                            override fun onError(e: Throwable?) {
                                loadError.value = true
                                loading.value = false

                            }

                        })
        )


    }


}