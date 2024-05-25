package com.example.mvvmretrofit.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmretrofit.pojo.Meal
// room will write the code for us
@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase:RoomDatabase() {
    abstract fun mealDao():MealDao
    companion object{
        @Volatile
        // used when change occur in one thread it will be visible to all other threads
        var Instance:MealDatabase?=null
        @Synchronized
        // means only one thread can be instance from this
   fun getInstance(context:Context):MealDatabase{
       if(Instance==null)
       {
           Instance=Room.databaseBuilder(context,MealDatabase::class.java,"meal.db").fallbackToDestructiveMigration().build()
       }
       return Instance as MealDatabase
   }
    }

}