package com.example.mvvmretrofit.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmretrofit.db.MealDatabase
import com.example.mvvmretrofit.pojo.Category
import com.example.mvvmretrofit.pojo.CategoryList
import com.example.mvvmretrofit.pojo.Meal
import com.example.mvvmretrofit.pojo.MealList
import com.example.mvvmretrofit.pojo.MealX
import com.example.mvvmretrofit.pojo.PopularMealList
import com.example.mvvmretrofit.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel():ViewModel() {
    private var categoryMealsLiveData =MutableLiveData<List<MealX>>()
    private var bottomSheetMealLiveData =MutableLiveData<Meal>()
    fun getMealsByCategory(categoryName:String)
    {
       RetrofitInstance.api.getCategory(categoryName).enqueue(object :Callback<PopularMealList>{
           override fun onResponse(
               call: Call<PopularMealList>,
               response: Response<PopularMealList>,
           ) {
               if(response.body()!=null)
               {
                   categoryMealsLiveData.value=response.body()!!.meals
               }
           }

           override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
               Log.d("CategoryMeals",t.message.toString())
           }

       })
    }
    fun observeMealLiveData():LiveData<List<MealX>>
    {
        return categoryMealsLiveData
    }
    fun getMealByIdCategory(id:String)
    {
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>
        {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null)
                {
                    val meal=response.body()!!.meals[0]
                    bottomSheetMealLiveData.value=meal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModelError",t.message.toString())
            }

        })
    }
    fun observeBottomSheetLiveDataCategory():LiveData<Meal>
    {
        return bottomSheetMealLiveData
    }

}