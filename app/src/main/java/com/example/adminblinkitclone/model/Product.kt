package com.example.adminblinkitclone.model

import android.text.Editable
import java.util.UUID

data class Product(

    var ProductTitle:String? = null,
    var noOfStock: Int? = null,
    var ProductImages:ArrayList<String?>? = null,
    var unit:String? = null,
    var quantity:Int? = null,
    var ProductId:String? = null,
    var Price:Int? = null,
    var adminId: String? = null,
    var ProductType:String ? = null,
    var ItemCount:Int? = null,
    var ProductCategory:String ? = null,
)
