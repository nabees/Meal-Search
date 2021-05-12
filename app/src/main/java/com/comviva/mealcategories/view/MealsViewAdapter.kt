package com.comviva.mealcategories.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.comviva.mealcategories.databinding.ItemMealBinding
import com.comviva.mealcategories.model.Meal
import com.comviva.mealcategories.util.getProgressDrawable
import com.comviva.mealcategories.util.loadImage


class MealsViewAdapter(private var mls: ArrayList<Meal>) :
    RecyclerView.Adapter<MealsViewAdapter.FoodViewHolder>() {

    fun updateMeals(newMeals: List<Meal>) {
        mls.clear()
        mls.addAll(newMeals)
        notifyDataSetChanged()

    }

    class FoodViewHolder(binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {
        private val mName = binding.mealName
        private val mImage = binding.MealImageView
        private val progressDrawable = getProgressDrawable(binding.root.context)
        fun bind(food: Meal) {
            mName.text = food.mealName
            mImage.loadImage(food.mealPic, progressDrawable)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(mls[position])
    }

    override fun getItemCount() = mls.size


}