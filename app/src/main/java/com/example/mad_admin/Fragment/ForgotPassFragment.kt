package com.example.mad_admin.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mad_admin.R
import com.example.mad_admin.databinding.FragmentForgotPassBinding
import com.example.mad_admin.viewmodel.AuthViewModal
import kotlinx.coroutines.launch


class ForgotPassFragment : Fragment() {

    lateinit var binding:FragmentForgotPassBinding
    private val viewModel:AuthViewModal by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentForgotPassBinding.inflate(layoutInflater)

        binding.btnRequestEmail.setOnClickListener{
            viewModel.sendPasswordResetEmail(requireContext(),binding.tvEmailForgot.text.toString())
        }

        viewModel.apply {
            lifecycleScope.launch {
                isEmailSent.collect{
                    if (it==true){
                        findNavController().navigate(R.id.action_forgotPassFragment_to_loginFragment)
                    }
                }
            }
        }

        return binding.root
    }


}