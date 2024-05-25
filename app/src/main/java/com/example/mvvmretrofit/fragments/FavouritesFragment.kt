package com.example.mvvmretrofit.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.Adapters.FavouritesMealsAdapter
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.activities.MainActivity
import com.example.mvvmretrofit.activities.MealActivity
import com.example.mvvmretrofit.databinding.FragmentFavouritesBinding
import com.example.mvvmretrofit.fragments.BottomSheet.MealBottomSheetFragment
import com.google.android.material.snackbar.Snackbar


class FavouritesFragment : Fragment() {
    private lateinit var binding:FragmentFavouritesBinding
  private lateinit var viewModel:HomeViewModel
  private lateinit var favouritesAdapter:FavouritesMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouritesAdapter = FavouritesMealsAdapter()
        observeFavourites()
        prepareRecyclerView()
        onFavourItemClicked()
        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                  val position= viewHolder.adapterPosition
                var k=favouritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(favouritesAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",View.OnClickListener {
                        viewModel.insertMeal(k)
                    }
                ).show()
            }

        }
  ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
        onLongItemClicked()
    }

    private fun onLongItemClicked() {
        favouritesAdapter.onLongClick ={
            val mealBottomSheetFragment= MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal_Info")
        }

    }

    private fun onFavourItemClicked() {
        favouritesAdapter.onClick ={meal->
            val intent= Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.Meal_Id,meal.idMeal)
            intent.putExtra(HomeFragment.Meal_Name,meal.strMeal)
            intent.putExtra(HomeFragment.Meal_Thumb,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun prepareRecyclerView() {

        binding.rvFavourites.apply {
            layoutManager= GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter =favouritesAdapter
        }
    }

    private fun observeFavourites() {
     viewModel.observeFavouritesLiveData().observe(requireActivity() ,Observer {meals->
         favouritesAdapter.differ.submitList(meals)

     })

    }


}