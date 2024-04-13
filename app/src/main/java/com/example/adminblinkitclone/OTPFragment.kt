package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.adminblinkitclone.AdminActivity
import com.example.adminblinkitclone.R
import com.example.adminblinkitclone.databinding.FragmentOTPBinding
import com.example.myapplication.model.Admins
import com.example.myapplication.utils.Utils
import com.example.myapplication.viewmodel.AuthviewModel
import kotlinx.coroutines.launch


class OTPFragment : Fragment() {

    private val viewModel: AuthviewModel by viewModels()
    private lateinit var binding: FragmentOTPBinding
    private lateinit var number : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(layoutInflater)

        getUserNumber()
        getCustomizingOTPNumber()
        sendOTP()
        onBackButtonclick()
        onLoginBtnclick()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onLoginBtnclick() {
        binding.btnLogin.setOnClickListener {
            Utils.showDialog(requireContext(),"Signing you...")
            val number = arrayOf(binding.etOTP1,binding.etOTP2,binding.etOTP3,binding.etOTP4,binding.etOTP5
                ,binding.etOTP6)
            val otp = number.joinToString(""){
                it.text.toString()
            }
            if (otp.length < number.size){
                Utils.Showtoastmsg(requireContext(),"Enter valid otp")
            }
            else{
                number.forEach {
                    it.text.clear()
                    it.clearFocus()
                }
                verify(otp)
            }
        }
    }

    private fun verify(otp: String) {
        var user = Utils.getCurrentUserId().let { Admins(uid = null, AdminPhoneNumber = number) }


        lifecycleScope.launch {
            viewModel.signInWithPhoneAuthCredential(otp,number,user)
            viewModel.SignInSuccessfully.collect{
                if (it){
                    Utils.hideDialog()
                    Utils.Showtoastmsg(requireContext(),"Logged In...")
                    val intent = Intent(requireContext(),AdminActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun sendOTP() {
        Utils.showDialog(requireContext(),"Sending OTP...")

        lifecycleScope.launch {
            viewModel.apply {
                sendOTP(number,requireActivity())
                otpsent.collect{
                    if (it){
                        Utils.hideDialog()
                        Utils.Showtoastmsg(requireContext(),"OTP sent")
                    }
                }
            }

        }
    }

    private fun onBackButtonclick() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }
    }

    private fun getCustomizingOTPNumber() {
        val number = arrayOf(binding.etOTP1,binding.etOTP2,binding.etOTP3,binding.etOTP4,binding.etOTP5
        ,binding.etOTP6)

        for(i in number.indices){
            number[i].addTextChangedListener(object:TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(otp: Editable?) {
                    if (otp != null) {
                        if (otp.length == 1){
                            if(i < number.size-1){
                                number[i+1].requestFocus()
                            }
                        }
                        else if(otp?.length == 0){
                            if (i > 0){
                                number[i-1].requestFocus()
                            }
                        }
                    }
                }

            })
        }
    }

    private fun getUserNumber() {
        val bundle = arguments
        number = bundle?.getString("number").toString()
        binding.tvUserNumber.text = number
    }

}