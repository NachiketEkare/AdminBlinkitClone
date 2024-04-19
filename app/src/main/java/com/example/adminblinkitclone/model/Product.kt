package com.example.adminblinkitclone.model

import android.text.Editable
import java.util.UUID

data class Product(

    var ProductId:String = UUID.randomUUID().toString(),
    var ProductTitle:String? = null,
    var quantity:Int? = null,
    var unit:String? = null,
    var Price:Int? = null,
    var noOfStock: String? = null,
    var ProductCategory:String ? = null,
    var ProductType:String ? = null,
    var ItemCount:Int? = null,
    var adminId: String? = null,
    var ProductImages:ArrayList<String?>? = null
)
