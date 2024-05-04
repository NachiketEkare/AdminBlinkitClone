package com.example.adminblinkitclone

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.adminblinkitclone.adapter.CategoryAdapter
import com.example.adminblinkitclone.adapter.ProductAdapter
import com.example.adminblinkitclone.databinding.FragmentHomeBinding
import com.example.adminblinkitclone.utils.Constants
import com.example.adminblinkitclone.viewmodel.adminViewModel
import com.example.myapplication.model.Category
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel:adminViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        setCategory()
        getAllProducts("All")
        setstatusBarColor()
        return binding.root

    }

    private fun getAllProducts(categories: String?) {
        binding.shimmerContainer.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.FetchAllProducts(categories).collect{

                if (it.isEmpty()){
                    binding.tvText.visibility = View.VISIBLE
                    binding.rvProducts.visibility = View.GONE
                }
                else{
                    binding.tvText.visibility = View.GONE
                    binding.rvProducts.visibility = View.VISIBLE
                }
                val productAdapter = ProductAdapter()
                binding.rvProducts.adapter = productAdapter
                productAdapter.differ.submitList(it)
                binding.shimmerContainer.visibility = View.GONE
            }
        }
    }

    private fun setCategory() {
        val categorylist = ArrayList<Category>()

        for (i in 0 until Constants.ProductListImage.size){
            categorylist.add(Category(Constants.allProductsCategory[i],Constants.ProductListImage[i]))
        }
        binding.rvCategories.adapter = CategoryAdapter(categorylist,::OnCategoryClicked)
    }

    private fun OnCategoryClicked(category:Category) {
        getAllProducts(category.productName)
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