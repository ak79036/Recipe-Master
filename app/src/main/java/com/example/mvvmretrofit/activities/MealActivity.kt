package com.example.mvvmretrofit.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.MealViewModel
import com.example.mvvmretrofit.ViewModel.MealViewModelFactory

import com.example.mvvmretrofit.databinding.ActivityMealBinding
import com.example.mvvmretrofit.db.MealDatabase
import com.example.mvvmretrofit.fragments.HomeFragment
import com.example.mvvmretrofit.pojo.Meal

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealMvvm:MealViewModel
    private lateinit var youtubelink:String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMealInformationfromIntent()
        setInformationInView()
        val mealDatabase=MealDatabase.getInstance(this)
        val mealViewModelFactory=MealViewModelFactory(mealDatabase)
        mealMvvm=ViewModelProvider(this,mealViewModelFactory)[MealViewModel::class.java]
//        mealMvvm=androidx.lifecycle.ViewModelProvider(this@MealActivity)[MealViewModel::class.java]
        onLoadingCase()
        mealMvvm.getMealInformation(mealId)
        observeMealImformation()
        onyoutubeimage()
        onFavouriteClicked()

    }

    private fun onFavouriteClicked() {
        binding.btnSave.setOnClickListener{
            mealtosave?.let {
              mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal Saved Successfully",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun onyoutubeimage() {
       binding.imgYoutube.setOnClickListener {
           val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubelink))
           startActivity(intent)
       }
    }
private var mealtosave:Meal?=null
    private fun observeMealImformation() {
       mealMvvm.observemeallivedata().observe(this,object :Observer<Meal>
       {
           override fun onChanged(value: Meal) {
               onResponseCase()
              val meals=value
               mealtosave=value
               binding.tvCategoryInfo.text = "Category: ${meals!!.strCategory}"
               binding.tvAreaInfo.text="Area: ${meals!!.strArea}"
               binding.tvContent.text=meals.strInstructions
               youtubelink= meals.strYoutube.toString()
           }

       })

    }

    private fun setInformationInView() {
        Glide.with(applicationContext).load(mealThumb).centerCrop().into(binding.imgMealDetail)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationfromIntent() {
 val intent=intent
       mealId= intent.getStringExtra(HomeFragment.Meal_Id)!!
        mealName=intent.getStringExtra(HomeFragment.Meal_Name)!!
        mealThumb=intent.getStringExtra(HomeFragment.Meal_Thumb)!!


    }
    private fun onLoadingCase()
    {
        binding.progressBar.visibility=View.VISIBLE
binding.btnSave.visibility= View.INVISIBLE
        binding.tvInstructions.visibility= View.INVISIBLE
        binding.tvCategoryInfo.visibility= View.INVISIBLE
        binding.tvAreaInfo.visibility= View.INVISIBLE
        binding.imgYoutube.visibility= View.INVISIBLE
    }
    private fun onResponseCase()
    {
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnSave.visibility= View.VISIBLE
        binding.tvInstructions.visibility= View.VISIBLE
        binding.tvCategoryInfo.visibility= View.VISIBLE
        binding.tvAreaInfo.visibility= View.VISIBLE
        binding.imgYoutube.visibility= View.VISIBLE
    }
}