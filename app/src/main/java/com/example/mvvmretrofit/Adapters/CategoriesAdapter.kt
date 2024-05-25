package com.example.mvvmretrofit.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmretrofit.databinding.CategoryItemBinding
import com.example.mvvmretrofit.pojo.Category
import com.example.mvvmretrofit.pojo.CategoryList

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>(){
  private var CategoryList =ArrayList<Category>()
    lateinit var OnItemClick :  ((Category)->Unit)
    fun setCategoryList(CategoryList:List<Category>)
    {
        this.CategoryList=CategoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    inner class CategoryViewHolder( var binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
           return CategoryViewHolder(
               CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
           )
    }

    override fun getItemCount(): Int {
return CategoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
Glide.with(holder.itemView).load(CategoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=CategoryList[position].strCategory
       holder.itemView.setOnClickListener{
           OnItemClick.invoke(
           CategoryList[position]
           )
       }


    }
}