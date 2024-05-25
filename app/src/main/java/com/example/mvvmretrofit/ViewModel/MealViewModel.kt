package com.example.mvvmretrofit.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmretrofit.db.MealDatabase
import com.example.mvvmretrofit.pojo.Meal
import com.example.mvvmretrofit.pojo.MealList
import com.example.mvvmretrofit.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
 val mealDatabase:MealDatabase
): ViewModel() {
    private  var randomMealLivedata = MutableLiveData<Meal>()
    fun getMealInformation(s:String)
    {
        RetrofitInstance.api.getMealDetails(s).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
              if(response.body()!=null)
              {
                   val MealInfo:Meal=response.body()!!.meals[0]
                  randomMealLivedata.value=MealInfo
              }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
          Log.d("MealActivity",t.message.toString())
            }

        })

    }
    fun observemeallivedata():LiveData<Meal>
    {
        return randomMealLivedata

    }
  fun insertMeal(meal:Meal)
  {
      viewModelScope.launch {
          mealDatabase.mealDao().upsert(meal)
      }
  }

}