package com.example.mad_admin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mad_admin.R
import com.example.mad_admin.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.tvLoginRegister.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


        return binding.root
    }


}