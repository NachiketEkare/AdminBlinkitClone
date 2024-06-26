package com.example.adminblinkitclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.adminblinkitclone.databinding.ItemviewCategoryBinding
import com.example.myapplication.model.Category
import kotlin.reflect.KFunction1

class CategoryAdapter(
    val categoryList:ArrayList<Category>
    , val OnCategoryClicked: KFunction1<Category, Unit>
):RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(ItemviewCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        var category = categoryList[position]
        holder.binding.apply {
            CategoryImageview.setImageResource(category.productImage)
            tvCategoryTitle.text = category.productName
        }
        holder.itemView.setOnClickListener {
            OnCategoryClicked(category)
        }

    }

    class CategoryHolder(val binding:ItemviewCategoryBinding):ViewHolder(binding.root)
}