package com.example.mvvmretrofit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmretrofit.pojo.Meal

@Dao
// Dao is an interface for writing updating deleting objects
// Data Access Objects
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("Select * From mealInformation")
    //live data cant be suspended
    fun getAllMeals():LiveData<List<Meal>>

}