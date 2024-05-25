package com.example.mvvmretrofit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.mvvmretrofit.Adapters.CategoriesAdapter
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.activities.CategoryMeals
import com.example.mvvmretrofit.activities.MainActivity
import com.example.mvvmretrofit.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {



    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var CategoriesAdapter: CategoriesAdapter
    private lateinit var viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= (activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentCategoriesBinding.inflate(inflater,container,false)
          return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparerecyclerView()
        observeCategories()
         oncategoriesitemclicked()

    }

    private fun oncategoriesitemclicked() {
        CategoriesAdapter.OnItemClick={category->
            val intent=Intent(activity,CategoryMeals::class.java)
            intent.putExtra(HomeFragment.Category_Name,category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner,{
            categories->
            CategoriesAdapter.setCategoryList(categories)
        })
    }

    private fun preparerecyclerView() {
        CategoriesAdapter= CategoriesAdapter()
      binding.rvCategoriesFragment.apply {
          layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
          adapter =CategoriesAdapter
      }
    }


}