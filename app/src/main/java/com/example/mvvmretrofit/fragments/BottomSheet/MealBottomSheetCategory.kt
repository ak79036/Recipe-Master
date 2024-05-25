package com.example.mvvmretrofit.fragments.BottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.ViewModel.CategoryMealsViewModel
import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.activities.CategoryMeals
import com.example.mvvmretrofit.activities.MealActivity
import com.example.mvvmretrofit.databinding.FragmentMealBottomSheetBinding
import com.example.mvvmretrofit.databinding.FragmentMealBottomSheetCategoryBinding
import com.example.mvvmretrofit.fragments.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MealID1 = "mealId1"



class MealBottomSheetCategory : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMealBottomSheetCategoryBinding
    private var mealid: String? = null
    private lateinit var viewmodel: CategoryMealsViewModel
    private var mealName:String?=null
    private var mealThumb:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel =(activity as CategoryMeals).categoryMealsViewModel
        arguments?.let {
            mealid = it.getString(MealID1)
    
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealid?.let {
            viewmodel.getMealByIdCategory(it)
        }
        observebottomsheetmeal()
        onbottomsheetitemclicked()
        
    }

    private fun onbottomsheetitemclicked() {
        binding.bottomSheetCategory.setOnClickListener {

            if(mealid!=null && mealName!=null && mealThumb!=null) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra(HomeFragment.Meal_Name, mealName)
                intent.putExtra(HomeFragment.Meal_Id, mealid)
                intent.putExtra(HomeFragment.Meal_Thumb, mealThumb)

                startActivity(intent)
                requireFragmentManager(). beginTransaction(). detach(this@MealBottomSheetCategory). commit()
            }


        }
    }

    private fun observebottomsheetmeal() {
        viewmodel.observeBottomSheetLiveDataCategory().observe(viewLifecycleOwner) { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgCategory)
            binding.tvMealCategory.text = meal.strCategory
            binding.tvMealCountry.text = meal.strArea
            binding.tvMealNameInBtmsheet.text = meal.strMeal
            mealName=meal.strMeal
            mealThumb=meal.strMealThumb
        }
    }

    companion object {

        @JvmStatic
        fun newInstance1(param1: String) =
            MealBottomSheetCategory().apply {
                arguments = Bundle().apply {
                    putString(MealID1, param1)

                }
            }
    }
}