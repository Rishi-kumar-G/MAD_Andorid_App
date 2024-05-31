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
import com.example.mad_admin.databinding.FragmentRegisterBinding
import com.example.mad_admin.viewmodel.AuthViewModal
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding

    private val viewModel: AuthViewModal by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater)


        binding.btnRegisterRegister.setOnClickListener{

            if (Utils.checkEmpty(binding.tvNameRegister)) binding.tvNameRegister.setError("Enter Name")
            else if (Utils.checkEmpty(binding.tvPhoneRegister)) binding.tvPhoneRegister.setError("Enter Phone Number")
            else if (Utils.checkEmpty(binding.tvEmailRegister)) binding.tvEmailRegister.setError("Enter Email")
            else if (Utils.checkEmpty(binding.tvPasswordRegister)) binding.tvPasswordRegister.setError("Enter Password")
            else if (Utils.checkEmpty(binding.tvCnfPasswordRegister)) binding.tvCnfPasswordRegister.setError("Enter Confirm Password")
            else if (Utils.isValidEmail(binding.tvEmailRegister.text.toString())) binding.tvEmailRegister.setError("Enter Valid Email")
            else {


                viewModel.registerUser(
                    requireContext(),
                    binding.tvEmailRegister.text.toString(),
                    binding.tvPasswordRegister.text.toString(),
                    binding.tvNameRegister.text.toString(),
                    binding.tvPhoneRegister.text.toString()
                )
            }
        }

        viewModel.apply {
            lifecycleScope.launch {
                isLoggedIn.collect{
                    if (it == true){
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                        requireActivity().finish()

                    }
                }
            }
        }

        binding.tvLoginRegister.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


        return binding.root
    }


}