package com.example.mad_admin.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mad_admin.Activity.MainActivity
import com.example.mad_admin.Utils
import com.example.mad_admin.databinding.FragmentSettingsBinding
import com.example.mad_admin.viewmodel.AuthViewModal




class SettingsFragment : Fragment() {


   lateinit var  binding : FragmentSettingsBinding

    private val AuthViewModal : AuthViewModal by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        val user = Utils.getUser(requireContext())
        binding.tvSettingName.setText(user.name)
        binding.tvSettingEmail.setText(user.email)
        binding.tvSettingPhone.setText(user.phone)
        binding.tvSettingClass.setText(user.standard.toString())
        binding.tvSettingSection.setText(user.section)


        binding.btnSettingsLogout.setOnClickListener {
            AuthViewModal.logout()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()

        }


        return binding.root
    }


}