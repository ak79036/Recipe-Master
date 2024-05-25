package com.example.mvvmretrofit.retrofit

import com.example.mvvmretrofit.pojo.CategoryList
import com.example.mvvmretrofit.pojo.MealList
import com.example.mvvmretrofit.pojo.PopularMealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String ):Call<MealList>
    // this question mark is only works because we call only on one query if we call more than one query then it crash in single get
    // simple fails in multiple query calls
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String):Call<PopularMealList>
    @GET("categories.php")
    fun getCategoryItems(): Call<CategoryList>
    @GET("filter.php?")
    fun getCategory(@Query("c") categoryName:String):Call<PopularMealList>
    @GET("search.php?")
    fun SearchMeals(@Query("s") searchQuery:String):Call<MealList>

}