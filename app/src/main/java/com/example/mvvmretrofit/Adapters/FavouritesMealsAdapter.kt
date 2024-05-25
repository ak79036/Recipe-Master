package com.example.mvvmretrofit.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.databinding.MealsCardBinding
import com.example.mvvmretrofit.pojo.Meal


class FavouritesMealsAdapter:RecyclerView.Adapter<FavouritesMealsAdapter.FavouritesMealsAdapterViewHolder>() {
    inner class FavouritesMealsAdapterViewHolder(val binding:MealsCardBinding):RecyclerView.ViewHolder(binding.root)

    // this run asyncronously and also it does work like notify data set changed but it does not refresh all data on the list but only the changed data
    // keeping other data as usual and increase the performance
    lateinit var onClick:((Meal)->Unit)
    lateinit var onLongClick:((Meal)->Unit)
    private val diffUtil =object :DiffUtil.ItemCallback<Meal>()
    {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            //compare the primary key
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           // just compare the objects

            return oldItem==newItem
        }

    }
  val differ =AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavouritesMealsAdapterViewHolder {
        return FavouritesMealsAdapterViewHolder(MealsCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))

}

    override fun getItemCount(): Int {
      return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavouritesMealsAdapterViewHolder, position: Int) {
        val meal =differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=meal.strMeal
        holder.itemView.setOnClickListener{
            onClick.invoke(differ.currentList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongClick.invoke(differ.currentList[position])
            true
        }
    }

}