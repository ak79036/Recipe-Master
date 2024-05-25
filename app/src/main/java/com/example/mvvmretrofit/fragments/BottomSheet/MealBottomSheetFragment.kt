package com.example.mvvmretrofit.fragments.BottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.clearFragmentResult
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.activities.MainActivity
import com.example.mvvmretrofit.activities.MealActivity
import com.example.mvvmretrofit.databinding.FragmentCategoriesBinding
import com.example.mvvmretrofit.databinding.FragmentMealBottomSheetBinding
import com.example.mvvmretrofit.fragments.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val Meal_Id = "mealId"



class MealBottomSheetFragment : BottomSheetDialogFragment() {
  private lateinit var binding: FragmentMealBottomSheetBinding
    private var mealId: String? = null
    private var mealName:String?=null
    private var mealThumb:String?=null
   private lateinit var viewmodel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         viewmodel= (activity as MainActivity).viewModel
        arguments?.let {
            mealId = it.getString(Meal_Id)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
      binding=FragmentMealBottomSheetBinding.inflate(inflater)
      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            viewmodel.getMealById(it)

        }
        observebottomsheetmeal()
        onbottomsheetitemclicked()


    }

    private fun onbottomsheetitemclicked() {
        binding.bottomSheet.setOnClickListener {

        if(mealId!=null && mealName!=null && mealThumb!=null) {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.Meal_Name, mealName)
            intent.putExtra(HomeFragment.Meal_Id, mealId)
            intent.putExtra(HomeFragment.Meal_Thumb, mealThumb)

            startActivity(intent)
            requireFragmentManager(). beginTransaction(). detach(this@MealBottomSheetFragment). commit()
        }


        }
    }

    private fun observebottomsheetmeal() {
        viewmodel.observeBottomSheetLiveData().observe(viewLifecycleOwner) { meal ->
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
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(Meal_Id, param1)

                }
            }
    }
}