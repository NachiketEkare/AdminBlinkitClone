package com.example.adminblinkitclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adminblinkitclone.databinding.FragmentItemOrderBinding


class ItemOrderFragment : Fragment() {

    private lateinit var binding: FragmentItemOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemOrderBinding.inflate(layoutInflater)
        return binding.root
    }

}