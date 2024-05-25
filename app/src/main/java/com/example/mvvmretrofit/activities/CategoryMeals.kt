package com.example.mvvmretrofit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvmretrofit.Adapters.CategoryMealsAdapter
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.CategoryMealsViewModel

import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.ViewModel.HomeViewModelFactory
import com.example.mvvmretrofit.ViewModel.MealViewModel
import com.example.mvvmretrofit.databinding.ActivityCategoryMealsBinding
import com.example.mvvmretrofit.db.MealDatabase
import com.example.mvvmretrofit.fragments.BottomSheet.MealBottomSheetCategory
import com.example.mvvmretrofit.fragments.BottomSheet.MealBottomSheetFragment
import com.example.mvvmretrofit.fragments.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryMeals : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var  categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
       setContentView(binding.root)


categoryMealsViewModel=androidx.lifecycle.ViewModelProvider(this@CategoryMeals)[CategoryMealsViewModel::class.java]
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.Category_Name)!!)
        prepareRecyclerView()
        categoryMealsViewModel.observeMealLiveData().observe(this, Observer {
            mealsList ->
                 categoryMealsAdapter.setMealList(mealsList)
                 binding.tvCategoryCount.text=mealsList.size.toString()

        })
        onCategoryItemsClicked()
        onCategoryItemLongClick()

    }

    private fun onCategoryItemLongClick() {
        categoryMealsAdapter.OnLongClick={meal->
            val mealBottomSheetFragment= MealBottomSheetCategory.newInstance1(meal.idMeal)
            mealBottomSheetFragment.show(supportFragmentManager,"Meal_Info1")

        }
    }

    private fun onCategoryItemsClicked() {
     categoryMealsAdapter.OnItemClick={
         val intent=Intent(this,MealActivity::class.java)
         intent.putExtra(HomeFragment.Meal_Id,it.idMeal)
         intent.putExtra(HomeFragment.Meal_Name,it.strMeal)
         intent.putExtra(HomeFragment.Meal_Thumb,it.strMealThumb)
         startActivity(intent)
     }
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
     binding.mealRecyclerview.apply {
         layoutManager =GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
         adapter =categoryMealsAdapter
     }
    }
}