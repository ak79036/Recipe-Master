package com.example.mvvmretrofit.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmretrofit.db.MealDatabase
//ViewModelFactory is a class in Android that is used to create instances of ViewModel classes.
// It is used when the ViewModel class requires additional arguments beyond the basic constructor
class HomeViewModelFactory(private val mealDatabase: MealDatabase):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }

}