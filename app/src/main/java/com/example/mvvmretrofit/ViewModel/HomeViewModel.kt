package com.example.mvvmretrofit.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmretrofit.db.MealDao
import com.example.mvvmretrofit.db.MealDatabase
import com.example.mvvmretrofit.pojo.Category
import com.example.mvvmretrofit.pojo.CategoryList

import com.example.mvvmretrofit.pojo.Meal
import com.example.mvvmretrofit.pojo.MealList
import com.example.mvvmretrofit.pojo.MealX
import com.example.mvvmretrofit.pojo.PopularMealList
import com.example.mvvmretrofit.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase:MealDatabase):ViewModel() {
 private  var randomMealLivedata = MutableLiveData<Meal>()
    private var popularitemslivedata = MutableLiveData<List<MealX>>()
    private var categoriesitemlivedata =MutableLiveData<List<Category>>()
    private var favouritesMealLiveData =mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData =MutableLiveData<Meal>()
    private var SearchMealsLiveData =MutableLiveData<List<Meal>>()
    var savestaterandommeal:Meal?=null
    fun getRandomMeal()
    {   savestaterandommeal?.let {randommeal->
        randomMealLivedata.value=randommeal
        return
    }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null)
                {
                    val randomMeal: Meal = response.body()!!.meals[0]
                     randomMealLivedata.value=randomMeal
                    savestaterandommeal=randomMeal
                }
                else
                {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment",t.message.toString())
            }


        }
        )
    }
    fun getPopularItems()
    {
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<PopularMealList>
        {
            override fun onResponse(
                call: Call<PopularMealList>,
                response: Response<PopularMealList>,
            ) {
               if(response.body()!=null)
               {

                   popularitemslivedata.value=response.body()!!.meals

               }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
             Log.d("Home Fragment Popular Meals",t.message.toString())
            }

        })
    }
    fun getCategoriesItems()
    {
        RetrofitInstance.api.getCategoryItems().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body()!=null)
                {
                    categoriesitemlivedata.value=response.body()!!.categories
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
        Log.d("Home Fragment Categories",t.message.toString())
            }

        })
    }
    fun deleteMeal(meal:Meal)
    {
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }
    fun insertMeal(meal:Meal)
    {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun searchMeals(searchQuery:String)
    {
  RetrofitInstance.api.SearchMeals(searchQuery).enqueue(object :Callback<MealList>
  {
      override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
         val meallist=response.body()?.meals
          meallist?.let {
              SearchMealsLiveData.value=it
          }
      }

      override fun onFailure(call: Call<MealList>, t: Throwable) {
          Log.e("HomeViewModelError",t.message.toString())
      }


  })

    }
    fun serachmealslivedata():LiveData<List<Meal>>
    {
        return SearchMealsLiveData
    }
    fun observeCategoryLiveData():LiveData<List<Category>>
    {
        return categoriesitemlivedata
    }
    fun observeLiveData():LiveData<Meal>
    {
        return randomMealLivedata
    }
    fun observePopularLiveData():LiveData<List<MealX>>
    {
        return popularitemslivedata
    }
    fun observeFavouritesLiveData():LiveData<List<Meal>>
    {
        return favouritesMealLiveData
    }
    fun getMealById(id:String)
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
    fun observeBottomSheetLiveData():LiveData<Meal>
    {
         return bottomSheetMealLiveData
    }
}