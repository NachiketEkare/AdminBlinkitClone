package com.example.adminblinkitclone

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.adminblinkitclone.adapter.ImageAdapter
import com.example.adminblinkitclone.databinding.FragmentItemOrderBinding
import com.example.adminblinkitclone.model.Product
import com.example.adminblinkitclone.utils.Constants
import com.example.myapplication.utils.Utils


class ItemOrderFragment : Fragment() {

    private lateinit var binding: FragmentItemOrderBinding
    val ImageUri:ArrayList<Uri> = arrayListOf()
    val selecctedImage = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){ListOfUri->
        val FiveImages = ListOfUri.take(5)
        ImageUri.clear()
        ImageUri.addAll(FiveImages)

        binding.rvProductImage.adapter = ImageAdapter(ImageUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemOrderBinding.inflate(layoutInflater)
        setAutoCompleteTextViews()
        setstatusBarColor()
        onImageBtnClicked()
        onAddBtnClicked()
        return binding.root
    }

    private fun onAddBtnClicked() {
        binding.addBtn.setOnClickListener {
            Utils.showDialog(requireContext(),"saving data...")
            val productTitle = binding.etProductTitle.text.toString()
            val quantity = binding.etQuantity.text.toString()
            val unit = binding.tvProductUnit.text.toString()
            val price = binding.etPrice.text.toString()
            val stock = binding.tvProductStock.text.toString()
            val productCategory = binding.ProductCategory.text.toString()
            val productType = binding.ProductType.text.toString()

            if(productTitle.isEmpty()||quantity.isEmpty()||unit.isEmpty()||price.isEmpty()||stock.isEmpty()||productCategory.isEmpty()||productType.isEmpty()){
                Utils.apply {
                    hideDialog()
                    showDialog(requireContext(),"Please fill all the fields")
                }
            }
            else if (ImageUri.isEmpty()){
                Utils.apply {
                    hideDialog()
                    showDialog(requireContext(),"Please upload some images")
                }
            }
            else{
                var product = Product(
                    ProductTitle = productTitle,
                    quantity = quantity.toInt(),
                    unit = unit,
                    Price = price.toInt(),
                    noOfStock = stock,
                    ProductCategory = productCategory,
                    ProductType = productType.toInt(),
                    ItemCount = 0,
                    adminId = Utils.getCurrentUserId()
                )
                SaveImageinFirebase(product)
            }
        }
    }

    private fun SaveImageinFirebase(product: Product) {


    }

    private fun onImageBtnClicked() {
        binding.ImageBtn.setOnClickListener {
            selecctedImage.launch("image/*")
        }
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