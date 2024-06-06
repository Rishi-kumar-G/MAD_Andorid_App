package com.example.mad_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mad_admin.databinding.FragmentEditBinding
import com.example.mad_admin.models.HomeWork
import com.example.mad_admin.viewmodel.MainViewModel


class EditFragment : Fragment() {

    lateinit var binding : FragmentEditBinding
    private val viewModel : MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)

        val homeWork = arguments?.getParcelable<HomeWork>("homeWork")
        if (homeWork!=null) {
            binding.apply {
                tvEditTitle.setText(homeWork.title)
                tvEditDesc.setText(homeWork.desc)
                tvEditClass.setText(homeWork.standard)
                tvEditSection.setText(homeWork.section)

            }

        }

        binding.btnEditSave.setOnClickListener{
            if (homeWork != null) {
//                viewModel.deleteHomeWork(requireContext(),homeWork)
                val old = "${homeWork.standard}${homeWork.section}"
                homeWork.title = binding.tvEditTitle.text.toString()
                homeWork.desc = binding.tvEditDesc.text.toString()
                homeWork.standard = binding.tvEditClass.text.toString()
                homeWork.section = binding.tvEditSection.text.toString()
                viewModel.updateHomWork(requireContext(),homeWork,old)
            }
        }


        return binding.root


    }


}