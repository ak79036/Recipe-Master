package com.example.mvvmretrofit.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    const val baseurl="https:themealdb.com/api/json/v1/1/"
    val api:MealApi by lazy {
  Retrofit.Builder().baseUrl(baseurl).
  addConverterFactory(GsonConverterFactory.create()).build().
  create(MealApi::class.java)

    }
}