package com.example.adminblinkitclone

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.adminblinkitclone.adapter.CategoryAdapter
import com.example.adminblinkitclone.adapter.ProductAdapter
import com.example.adminblinkitclone.databinding.EditproductlayoutBinding
import com.example.adminblinkitclone.databinding.FragmentHomeBinding
import com.example.adminblinkitclone.model.Product
import com.example.adminblinkitclone.utils.Constants
import com.example.adminblinkitclone.viewmodel.adminViewModel
import com.example.myapplication.model.Category
import com.example.myapplication.utils.Utils
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
                val productAdapter = ProductAdapter(::onEditBtnCLicked)
                binding.rvProducts.adapter = productAdapter
                productAdapter.differ.submitList(it)
                binding.shimmerContainer.visibility = View.GONE
            }
        }
    }

    private fun onEditBtnCLicked(product: Product){
        lifecycleScope.launch{
            try {

                val editProduct = EditproductlayoutBinding.inflate(LayoutInflater.from(requireContext()))
                editProduct.apply {
                    etProductTitle.setText(product.ProductTitle)
                    etQuantity.setText(product.quantity.toString())
                    tvProductUnit.setText(product.unit)
                    etPrice.setText(product.Price.toString())
                    tvProductStock.setText(product.noOfStock.toString())
                    ProductCategory.setText(product.ProductCategory)
                    ProductType.setText(product.ProductType)
                }
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setView(editProduct.root)
                    .create()
                alertDialog.show()

                editProduct.EditBtn.setOnClickListener {
                    editProduct.etProductTitle.isEnabled = true
                    editProduct.etQuantity.isEnabled = true
                    editProduct.tvProductUnit.isEnabled = true
                    editProduct.etPrice.isEnabled = true
                    editProduct.tvProductStock.isEnabled = true
                    editProduct.ProductCategory.isEnabled = true
                    editProduct.ProductType.isEnabled = true
                }
                setAutoCompleteTextViews(editProduct)

                editProduct.saveBtn.setOnClickListener {
                    lifecycleScope.launch {
                        product.ProductTitle = editProduct.etProductTitle.text.toString()
                        product.quantity = editProduct.etQuantity.text.toString().toInt()
                        product.unit = editProduct.tvProductUnit.text.toString()
                        product.ProductType = editProduct.ProductType.text.toString()
                        product.noOfStock = editProduct.tvProductStock.text.toString().toInt()
                        product.ProductCategory = editProduct.ProductCategory.text.toString()
                        product.Price = editProduct.etPrice.text.toString().toInt()
                        viewModel.saveUpdatedDetails(product)
                    }
                    Utils.Showtoastmsg(requireContext(),"saved changes")
                    alertDialog.dismiss()
                }

            }
            catch (e:Exception){
                println("Error converting product: ${e.message}")
            }
        }
    }

    private fun setAutoCompleteTextViews(editproduct: EditproductlayoutBinding) {
        val units = ArrayAdapter(requireContext(),R.layout.show_list,Constants.allUnitsOfProducts)
        val category = ArrayAdapter(requireContext(),R.layout.show_list,Constants.allProductsCategory)
        val productType = ArrayAdapter(requireContext(),R.layout.show_list,Constants.allProductType)
        val stock = ArrayAdapter(requireContext(),R.layout.show_list,Constants.stock)

        editproduct.apply {
            tvProductUnit.setAdapter(units)
            ProductCategory.setAdapter(category)
            ProductType.setAdapter(productType)
            tvProductStock.setAdapter(stock)
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