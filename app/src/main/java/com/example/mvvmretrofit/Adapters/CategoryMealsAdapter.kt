package com.example.mvvmretrofit.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.activities.CategoryMeals
import com.example.mvvmretrofit.databinding.MealsCardBinding
import com.example.mvvmretrofit.pojo.Category
import com.example.mvvmretrofit.pojo.Meal
import com.example.mvvmretrofit.pojo.MealX

class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
    private var mealList= ArrayList<MealX>()
    lateinit var OnItemClick :  ((MealX)->Unit)
    lateinit var OnLongClick: ((MealX)->Unit)
    fun setMealList(categoryList:List<MealX>)
    {
        this.mealList=categoryList as ArrayList<MealX>
        notifyDataSetChanged()
    }
  inner class CategoryMealsViewModel(var binding:MealsCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        return CategoryMealsViewModel(
            MealsCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
  return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
Glide.with(holder.itemView).load(mealList[position].strMealThumb).centerCrop().into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=mealList[position].strMeal
          holder.itemView.setOnClickListener{
            OnItemClick.invoke(mealList[position])
        }
        holder.itemView.setOnLongClickListener {
            OnLongClick.invoke(mealList[position])
            true
        }
    }


}