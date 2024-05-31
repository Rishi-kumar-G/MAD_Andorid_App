package com.example.mad_admin.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mad_admin.Activity.HomeActivity
import com.example.mad_admin.R
import com.example.mad_admin.databinding.FragmentSplashBinding
import com.example.mad_admin.viewmodel.AuthViewModal


class SplashFragment : Fragment() {

    lateinit var binding:FragmentSplashBinding
    private val viewModal: AuthViewModal by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentSplashBinding.inflate(inflater)
        // Inflate the layout for this fragment


        Handler().postDelayed({
            if (viewModal.getAuthInstance().currentUser!=null){
                startActivity(Intent(requireContext(), HomeActivity::class.java))
                requireActivity().finish()
            }
            else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        },2000)




        return binding.root
    }


}