package com.comviva.mealcategories.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.comviva.mealcategories.databinding.ActivityMainBinding
import com.comviva.mealcategories.viewmodel.ListViewModel

class MainActivity : AppCompatActivity(), CategoriesListAdapter.OnItemClickListener {
    private lateinit var viewModel: ListViewModel
    private val categoryAdapter = CategoriesListAdapter(arrayListOf(), this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()
        binding.categoriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoryAdapter
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.categories.observe(this, { meals ->
            meals?.let {
                binding.categoriesList.visibility = View.VISIBLE
                categoryAdapter.updateCategories(it)
            }
        })

        viewModel.loadError.observe(this, { isError ->
            isError?.let { binding.listError.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.categoriesList.visibility = View.GONE
                    binding.listError.visibility = View.GONE
                }
            }
        })

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(
            this,
            "${categoryAdapter.categories[position].categoryName} clicked",
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(this, MealsViewActivity::class.java).apply {
            putExtra("mn", categoryAdapter.categories[position].categoryName)
        }
        startActivity(intent)
    }

}