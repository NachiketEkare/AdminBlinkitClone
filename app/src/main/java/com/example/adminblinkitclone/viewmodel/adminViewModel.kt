package com.example.adminblinkitclone.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.adminblinkitclone.model.Product
import com.example.myapplication.utils.Utils
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import java.util.concurrent.CountDownLatch

class adminViewModel:ViewModel() {

    private val _isImagesUploaded = MutableStateFlow(false)
    var isImageUploaded:StateFlow<Boolean> = _isImagesUploaded

    private val _isProductSaved = MutableStateFlow(false)
    var isProductSaved:StateFlow<Boolean> = _isProductSaved

    private var isDownloadedUri = MutableStateFlow<ArrayList<String?>>(arrayListOf())
    var _isDownloadedUri:StateFlow<ArrayList<String?>> = isDownloadedUri

    fun saveImageInDB(imageUri:ArrayList<Uri>){

        val downloadUri = ArrayList<String?>()
        val latch = CountDownLatch(imageUri.size)
        imageUri.forEach { Uri ->
            val imageRef = FirebaseStorage.getInstance().reference.child("ProductImages").child("images/").child(UUID.randomUUID().toString())
            imageRef.putFile(Uri).addOnSuccessListener { uploadTask ->
                uploadTask.storage.downloadUrl.addOnSuccessListener { uri ->
                    downloadUri.add(uri.toString())
                    latch.countDown()
                    if (latch.count == 0L) {
                        _isImagesUploaded.value = true
                        isDownloadedUri.value = downloadUri
                    }
                }.addOnFailureListener { exception ->
                    // Handle failure to get download URL
                }
            }.addOnFailureListener { exception ->
                // Handle failure to upload file
            }

        }
    }

    fun saveProductDetails(product: Product){
        FirebaseDatabase.getInstance().getReference("Admins").child("AllProducts/").setValue(product)
            .addOnSuccessListener{
                FirebaseDatabase.getInstance().getReference("Admins").child("ProductCategory/${product.ProductId}").setValue(product)
                    .addOnSuccessListener {
                        FirebaseDatabase.getInstance().getReference("Admins").child("ProductType/${product.ProductId}").setValue(product)
                            .addOnSuccessListener {
                                _isProductSaved.value = true
                            }
                    }
            }
    }

    }