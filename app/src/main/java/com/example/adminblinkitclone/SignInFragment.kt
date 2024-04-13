package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.adminblinkitclone.R
import com.example.adminblinkitclone.databinding.FragmentSignInBinding
import com.example.myapplication.utils.Utils

/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater)

        getNumberCount()
        onContinueButtonClick()
        return binding.root
    }

    private fun onContinueButtonClick() {
        binding.ContinueBtn.setOnClickListener {
            val number = binding.etMobileNumber.text.toString()
            if(number.length!=10 || number.isEmpty()){
                Utils.Showtoastmsg(requireContext(),"Please enter valid number.")
            }
            else{
                val bundle = Bundle()
                bundle.putString("number",number)
                findNavController().navigate(R.id.action_signInFragment_to_OTPFragment,bundle)
            }
        }
    }

    private fun getNumberCount() {
        binding.etMobileNumber.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val len = number?.length
                if(len == 10){
                    binding.ContinueBtn.setBackgroundColor(ContextCompat.getColor(requireContext(),
                        R.color.green))
                }
                else{
                    binding.ContinueBtn.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

}