package com.example.mvvmretrofit.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.databinding.PopularItemsBinding
import com.example.mvvmretrofit.pojo.Meal
import com.example.mvvmretrofit.pojo.MealX
import com.example.mvvmretrofit.pojo.PopularMealList


class MostPopular():RecyclerView.Adapter<MostPopular.MostPopularViewHolder>() {

   private var mealList = ArrayList<MealX>()
 lateinit var onClick:((MealX)->Unit)
    lateinit var onLongitemClick:((MealX)->Unit)
    fun updateMealList(mealList:ArrayList<MealX>)
    {
        this.mealList=mealList
        notifyDataSetChanged()

    }
    class MostPopularViewHolder(val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
  return MostPopularViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
  return mealList.size
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {
  Glide.with(holder.itemView).load(mealList[position].strMealThumb).centerCrop().into(holder.binding.imgPopularMeal)
        holder.itemView.setOnClickListener{
            onClick.invoke(mealList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongitemClick.invoke(mealList[position])
            true
        }
    }



}