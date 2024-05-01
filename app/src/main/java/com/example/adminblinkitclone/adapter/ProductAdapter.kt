package com.example.adminblinkitclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.adminblinkitclone.databinding.ItemviewProductBinding
import com.example.adminblinkitclone.model.Product

class ProductAdapter:RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(val binding:ItemviewProductBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(ItemviewProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    val diffUtil = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.ProductId == newItem.ProductId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.apply {
            val imagelist = ArrayList<SlideModel>()
            val productImage = product.ProductImages

            for (i in 0 until productImage?.size!!){
                imagelist.add(SlideModel(product.ProductImages!![i].toString()))
            }
            imageSlider.setImageList(imagelist)
            ProductTitle.text = product.ProductTitle
            ProductPrice.text = "₹" + product.Price.toString()
            val quantity = product.quantity.toString() + product.unit
            ProductQuantity.text = quantity
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}