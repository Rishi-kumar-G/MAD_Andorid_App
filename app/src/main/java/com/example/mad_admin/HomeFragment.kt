package com.example.mad_admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mad_admin.Activity.MainActivity
import com.example.mad_admin.databinding.ActivityHomeBinding
import com.example.mad_admin.databinding.FragmentHomeBinding
import com.example.mad_admin.databinding.FragmentSplashBinding
import com.example.mad_admin.viewmodel.AuthViewModal


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val Auth : AuthViewModal by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.tvLogout.setOnClickListener{
            Auth.logout()
            startActivity(Intent(requireContext(),MainActivity::class.java))
        }

        return binding.root
    }


}