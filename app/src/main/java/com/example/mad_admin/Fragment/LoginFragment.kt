package com.example.mad_admin.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mad_admin.Activity.HomeActivity
import com.example.mad_admin.R
import com.example.mad_admin.Utils
import com.example.mad_admin.databinding.FragmentLoginBinding
import com.example.mad_admin.viewmodel.AuthViewModal
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModal by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.tvRegisterLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLoginLogin.setOnClickListener{

            if (Utils.checkEmpty(binding.tvLoginEmail)) Utils.setError(binding.tvLoginEmail,"Enter A Valid Email")
            else if(Utils.checkEmpty(binding.tvPasswordLogin)) Utils.setError(binding.tvPasswordLogin,"Enter Password...")
            else {
                viewModel.loginUser(
                    requireContext(),
                    binding.tvLoginEmail.text.toString(),
                    binding.tvPasswordLogin.text.toString()
                )
            }
        }

        binding.tvForgotLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPassFragment)
        }

        viewModel.apply {
            lifecycleScope.launch {
                isLoggedIn.collect{
                    if (it==true){
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }

        return binding.root
    }


}