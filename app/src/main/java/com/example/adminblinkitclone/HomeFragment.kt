package com.example.adminblinkitclone

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adminblinkitclone.adapter.CategoryAdapter
import com.example.adminblinkitclone.databinding.FragmentHomeBinding
import com.example.adminblinkitclone.utils.Constants
import com.example.myapplication.model.Category


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        setCategory()
        setstatusBarColor()
        return binding.root

    }

    private fun setCategory() {
        val categorylist = ArrayList<Category>()

        for (i in 0 until Constants.ProductListImage.size){
            categorylist.add(Category(Constants.allProductsCategory[i],Constants.ProductListImage[i]))
        }
        binding.rvCategories.adapter = CategoryAdapter(categorylist)
    }

    private fun setstatusBarColor() {
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(),R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}