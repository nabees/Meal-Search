package com.comviva.mealcategories.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.comviva.mealcategories.databinding.ActivityMealsViewBinding
import com.comviva.mealcategories.viewmodel.ListViewModel

class MealsViewActivity : AppCompatActivity() {
    private lateinit var viewModel: ListViewModel
    private val mealAdapter = MealsViewAdapter(arrayListOf())
    private lateinit var binding: ActivityMealsViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        val bundle = intent.extras
        viewModel.c = bundle?.get("mn").toString()
        binding.foodName.text = viewModel.c
        viewModel.refreshMeals()
        binding.mealsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mealAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.meals.observe(this, { meals ->
            meals?.let {
                binding.mealsList.visibility = View.VISIBLE
                mealAdapter.updateMeals(it)
            }
        })

        viewModel.mloadError.observe(this, { isError ->
            isError?.let { binding.mealsListError.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.mloading.observe(this, { isLoading ->
            isLoading?.let {
                binding.mealsLoadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.mealsList.visibility = View.GONE
                    binding.mealsListError.visibility = View.GONE
                }
            }
        })
    }
}