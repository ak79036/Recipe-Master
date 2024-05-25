package com.example.mvvmretrofit.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.Adapters.CategoriesAdapter
import com.example.mvvmretrofit.Adapters.MostPopular
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.ViewModel.HomeViewModel
import com.example.mvvmretrofit.activities.CategoryMeals
import com.example.mvvmretrofit.activities.MainActivity
import com.example.mvvmretrofit.activities.MealActivity
import com.example.mvvmretrofit.databinding.FragmentHomeBinding
import com.example.mvvmretrofit.fragments.BottomSheet.MealBottomSheetFragment
import com.example.mvvmretrofit.pojo.Meal
import com.example.mvvmretrofit.pojo.MealX


class HomeFragment : Fragment() {
 private lateinit var binding:FragmentHomeBinding
private lateinit var viewModel :HomeViewModel
private var randomMeal:Meal ?=null
private lateinit var popularItemAdapter:MostPopular
private lateinit var categoriesAdapter: CategoriesAdapter
companion object
{
      const val Meal_Id="com.example.mvvmretrofit.fragments.idMeal"
    const val Meal_Name="com.example.mvvmretrofit.fragments.nameMeal"
    const val Meal_Thumb="com.example.mvvmretrofit.fragments.thumbMeal"
    const val Category_Name ="com.example.mvvmretrofit.fragments.Category_Name"

}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      viewModel=androidx.lifecycle.ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]
        viewModel= (activity as MainActivity).viewModel


    }

    private fun observePopularItems() {
     viewModel.observePopularLiveData().observe(viewLifecycleOwner
     ) {
         meallist->
       popularItemAdapter.updateMealList(meallist as ArrayList<MealX>)
     }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        popularItemAdapter = MostPopular()
        categoriesAdapter= CategoriesAdapter()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       viewModel.getRandomMeal()
        observerRandomMeal()
       oncardclicked()

        setupRecyclerView()
        viewModel.getPopularItems()
        observePopularItems()
        onpopularitemClicked()
        prepareCategoryRecycler()
        viewModel.getCategoriesItems()
        observeCategoryItems()
        onItemClickCategory()
        onPopularItemLongClick()
        onRandomMealLongItemClick()
        onsearchiconclick()
    }

    private fun onsearchiconclick() {

        binding.imgSearch.setOnClickListener{

            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onRandomMealLongItemClick() {
        binding.imgRandomMeal.setOnLongClickListener {
            if(randomMeal!=null)
            {
                val mealBottomSheetFragment=MealBottomSheetFragment.newInstance(randomMeal!!.idMeal)
                mealBottomSheetFragment.show(childFragmentManager,"Meal_Info")
            }
            true
        }


    }

    private fun onPopularItemLongClick() {
        popularItemAdapter.onLongitemClick={
            val mealBottomSheetFragment=MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal_Info")
        }
    }

    private fun onItemClickCategory() {
         categoriesAdapter.OnItemClick ={category->
             val intent=Intent(activity,CategoryMeals::class.java)
             intent.putExtra(Category_Name,category.strCategory)
              startActivity(intent)

         }
    }

    private fun prepareCategoryRecycler() {
        binding.recyclerView.apply {
            layoutManager =GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter =categoriesAdapter
        }
    }

    private fun observeCategoryItems() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner
        ) {
                categories->

                categoriesAdapter.setCategoryList(categories)


        }

    }

    private fun onpopularitemClicked() {
        popularItemAdapter.onClick = {
            meals->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(Meal_Id,meals.idMeal)
            intent.putExtra(Meal_Name,meals.strMeal)
            intent.putExtra(Meal_Thumb,meals.strMealThumb)
            startActivity(intent)

        }
    }

    private fun setupRecyclerView() {
         binding.recViewMealsPopular.apply {
             layoutManager =LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
             adapter =popularItemAdapter
         }

    }

    private fun oncardclicked() {
   binding.imgRandomMeal.setOnClickListener {
       val intent=Intent(activity,MealActivity::class.java)
       intent.putExtra(Meal_Id,randomMeal!!.idMeal)
       intent.putExtra(Meal_Name,randomMeal!!.strMeal)
       intent.putExtra(Meal_Thumb,randomMeal!!.strMealThumb)
       startActivity(intent)
   }


    }

    private fun observerRandomMeal() {

        viewModel.observeLiveData().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
               randomMeal=value
                Glide.with(this@HomeFragment).load(value.strMealThumb).centerCrop().into(binding.imgRandomMeal)

            }

        })
    }


}