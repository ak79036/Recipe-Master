package com.example.mvvmretrofit.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofit.Adapters.FavouritesMealsAdapter
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.activities.MainActivity
import com.example.mvvmretrofit.activities.MealActivity
import com.example.mvvmretrofit.databinding.FragmentSearchBinding
import com.example.mvvmretrofit.fragments.BottomSheet.MealBottomSheetCategory
import com.example.mvvmretrofit.fragments.BottomSheet.MealBottomSheetFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

private lateinit var binding:FragmentSearchBinding
private lateinit var viewmodel:HomeViewModel
private lateinit var searchRecyclerViewAdapter:FavouritesMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater)

       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        binding.icSearch.setOnClickListener{
            searchMeals()
        }
        observeSearchMealsLiveData()
           var searchJob:Job?=null
             binding.edSearch.addTextChangedListener {seachquery->
            searchJob?.cancel()
            // starting a new coroutines for this job

            searchJob=lifecycleScope.launch {
                delay(500)
               viewmodel.searchMeals(seachquery.toString())
            }
        }
        onitemclick()
        onlongitemclick()


    }

    private fun onlongitemclick() {
        searchRecyclerViewAdapter.onLongClick={  meal->

            val mealBottomSheetFragment= MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal_Info")
        }
    }

    private fun onitemclick() {

        searchRecyclerViewAdapter.onClick={meal->



            val intent= Intent(context,MealActivity::class.java)
            intent.putExtra(HomeFragment.Meal_Id,meal.idMeal)
            intent.putExtra(HomeFragment.Meal_Name,meal.strMeal)
            intent.putExtra(HomeFragment.Meal_Thumb,meal.strMealThumb)

            startActivity(intent)

        }

    }

    private fun observeSearchMealsLiveData() {
        viewmodel.serachmealslivedata().observe(viewLifecycleOwner) { mealslist ->
            searchRecyclerViewAdapter.differ.submitList(mealslist)


        }
    }

    private fun searchMeals() {
        val searchQuery=binding.edSearch.text.toString()
        if(searchQuery.isNotEmpty())
        {
            viewmodel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdapter= FavouritesMealsAdapter()
        binding.recyclerSearchedMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchRecyclerViewAdapter
        }
    }

}