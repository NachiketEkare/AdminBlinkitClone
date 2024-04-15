package com.example.adminblinkitclone.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.adminblinkitclone.databinding.ItemViewImageSelectionBinding

class ImageAdapter(
    val ImageArray:ArrayList<Uri>
):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(val binding: ItemViewImageSelectionBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemViewImageSelectionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return ImageArray.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageArray = ImageArray[position]
        holder.binding.apply {
            IvImageBtn.setImageURI(imageArray)
        }
        holder.binding.IvCloseBtn.setOnClickListener {
            if(position < ImageArray.size){
                ImageArray.removeAt(position)
                notifyItemRemoved(position)
            }

        }
    }
}