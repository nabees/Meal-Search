package com.comviva.mealcategories

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.comviva.mealcategories.view.CategoriesListAdapter
import com.comviva.mealcategories.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppUITest {
    @get:Rule
    val rule=ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun recyclerViewVisibilityTest() {
        SystemClock.sleep(2000)
        onView(withId(R.id.categoriesList)).check(matches(isDisplayed()))
    }

    @Test
    fun onItemClickTest(){
        SystemClock.sleep(2000)
        onView(withId(R.id.categoriesList)).perform(actionOnItemAtPosition<CategoriesListAdapter.MealViewHolder>(4, click()))
        SystemClock.sleep(2000)
        onView(withId(R.id.foodName)).check(matches(withText("Miscellaneous")))
    }

    @Test
    fun scrollRecyclerViewTest(){
        SystemClock.sleep(2000)
        onView(withId(R.id.categoriesList)).perform(scrollTo<CategoriesListAdapter.MealViewHolder>(
            hasDescendant(withText("Vegan"))))
    }


}