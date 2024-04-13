package com.example.myapplication.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Admins
import com.example.myapplication.utils.Utils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthviewModel() : ViewModel() {

    private var _verificationid = MutableStateFlow<String?>(null)
    private val _otpsent = MutableStateFlow(false)
    val otpsent = _otpsent
    private val _isSignInSuccessfully = MutableStateFlow(false)
    val SignInSuccessfully = _isSignInSuccessfully
    private val _isCurrentUser = MutableStateFlow(false)
    val isCurrentUser = _isCurrentUser

    init {
        FirebaseAuth.getInstance().currentUser.let {
            _isCurrentUser.value = true
        }
    }


    fun sendOTP(userNumber: String, activity: Activity) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                _verificationid.value = verificationId
                otpsent.value = true
            }
        }
        Utils.firebaseinstance().firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
        val options = PhoneAuthOptions.newBuilder(Utils.firebaseinstance())
            .setPhoneNumber("+91${userNumber}") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(otp: String, userNumber: String, user: Admins) {

        val credential = PhoneAuthProvider.getCredential(_verificationid.value.toString(), otp)
        Utils.firebaseinstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    FirebaseDatabase.getInstance().getReference("Admins").child("admin")
                        .child(user.AdminPhoneNumber).setValue(user)
                }
                SignInSuccessfully.value = true
            }
    }
}