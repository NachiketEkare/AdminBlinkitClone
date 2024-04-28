package com.example.adminblinkitclone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.adminblinkitclone.databinding.ItemviewProductBinding
import com.example.adminblinkitclone.model.Product

class ProductAdapter(val ProductList:ArrayList<Product>):RecyclerView.Adapter<ProductAdapter.ProductViewHoldder>() {
    class ProductViewHoldder(val binding:ItemviewProductBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHoldder {
        return ProductViewHoldder(ItemviewProductBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHoldder, position: Int) {
        val product = ProductList[position]
        holder.binding.apply {
            ProductTitle.text = product.ProductTitle
            ProductPrice.text = product.Price.toString()
            ProductQuantity.text = product.quantity.toString()
        }
    }

    override fun getItemCount(): Int {
        return ProductList.size
    }
}