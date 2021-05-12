package com.comviva.mealcategories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.comviva.mealcategories.model.*
import com.comviva.mealcategories.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var appService: AppDataService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle1: Single<Categories>? = null
    private var testSingle2: Single<Meals>? = null

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker { it.run() }
            }

        }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }


    @Test
    fun getCategoriesSuccess() {
        val category = arrayListOf(Category("name", "desc", "url"))
        val categoriesList = Categories(category)
        testSingle1 = Single.just(categoriesList)
        `when`(appService.getCategories()).thenReturn(testSingle1)
        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.categories.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)

    }

    @Test
    fun getCategoriesFail() {
        testSingle1 = Single.error(Throwable())

        `when`(appService.getCategories()).thenReturn(testSingle1)

        listViewModel.refresh()

        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getMealsSuccess() {
        val meal = arrayListOf(Meal("name", "url"))
        val mealsList = Meals(meal)
        listViewModel.c = "Chicken"
        testSingle2 = Single.just(mealsList)
        `when`(appService.getMeals(listViewModel.c)).thenReturn(testSingle2)
        listViewModel.refreshMeals()

        Assert.assertEquals(1, listViewModel.meals.value?.size)
        Assert.assertEquals(false, listViewModel.mloadError.value)
        Assert.assertEquals(false, listViewModel.mloading.value)
    }

    @Test
    fun getMealsFail() {

        testSingle2 = Single.error(Throwable())
        listViewModel.c = ""

        `when`(appService.getMeals(listViewModel.c)).thenReturn(testSingle2)

        listViewModel.refreshMeals()

        Assert.assertEquals(true, listViewModel.mloadError.value)
        Assert.assertEquals(false, listViewModel.mloading.value)

    }


}