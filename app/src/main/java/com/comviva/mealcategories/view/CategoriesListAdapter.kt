package com.comviva.mealcategories.view


import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.recyclerview.widget.RecyclerView
import com.comviva.mealcategories.model.Category
import com.comviva.mealcategories.databinding.ItemCategoryBinding
import com.comviva.mealcategories.util.getProgressDrawable
import com.comviva.mealcategories.util.loadImage

class CategoriesListAdapter(
    var categories: ArrayList<Category>,
    val listener: OnItemClickListener
) :
        RecyclerView.Adapter<CategoriesListAdapter.MealViewHolder>() {

    fun updateCategories(newCategories: List<Category>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()

    }

    inner class MealViewHolder(binding: ItemCategoryBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private val categoryName = binding.name
        private val categoryDescription = binding.description
        private val image = binding.imageView
        private val progressDrawable = getProgressDrawable(binding.root.context)


        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(meal: Category) {
            categoryName.text = meal.categoryName

            if (meal.categoryDescription!!.length > 100) {

                val text1: String = meal.categoryDescription.substring(0, 100)
                val ss = SpannableString("$text1...View More")

                ss[103..112] = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        val text2: String = meal.categoryDescription + "...View Less"
                        val ss2 = SpannableString(text2)
                        ss2[text2.length - 9..text2.length] = object : ClickableSpan() {
                            override fun onClick(widget: View) {
                                categoryDescription.text = ss
                            }

                            override fun updateDrawState(ds: TextPaint) {
                                super.updateDrawState(ds)
                                ds.color = Color.BLUE
                                ds.typeface = Typeface.DEFAULT_BOLD
                            }
                        }
                        categoryDescription.movementMethod = LinkMovementMethod()
                        categoryDescription.text = ss2
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = Color.BLUE
                        ds.typeface = Typeface.DEFAULT_BOLD
                    }
                }

                categoryDescription.movementMethod = LinkMovementMethod()
                categoryDescription.text = ss

            } else {

                categoryDescription.text = meal.categoryDescription
            }

            image.loadImage(meal.categoryPic, progressDrawable)

        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            listener.onItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding =
                ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}