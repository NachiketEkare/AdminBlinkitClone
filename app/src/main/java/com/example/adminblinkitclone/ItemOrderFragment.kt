package com.example.adminblinkitclone

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.adminblinkitclone.databinding.FragmentItemOrderBinding
import com.example.adminblinkitclone.utils.Constants
import com.example.myapplication.model.Category


class ItemOrderFragment : Fragment() {

    private lateinit var binding: FragmentItemOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemOrderBinding.inflate(layoutInflater)
        setAutoCompleteTextViews()
        setstatusBarColor()
        return binding.root


    }

    private fun setAutoCompleteTextViews() {
        val units = ArrayAdapter(requireContext(),R.layout.show_list,Constants.allUnitsOfProducts)
        val category = ArrayAdapter(requireContext(),R.layout.show_list,Constants.allProductsCategory)
        val productType = ArrayAdapter(requireContext(),R.layout.show_list,Constants.allProductType)

        binding.apply {
            tvProductUnit.setAdapter(units)
            ProductCategory.setAdapter(category)
            ProductType.setAdapter(productType)
        }
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