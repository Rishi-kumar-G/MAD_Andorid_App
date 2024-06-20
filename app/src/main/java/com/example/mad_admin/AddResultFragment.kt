package com.example.mad_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mad_admin.databinding.FragmentAddResultBinding
import com.example.mad_admin.databinding.FragmentChatsBinding
import com.example.mad_admin.models.Result
import com.example.mad_admin.models.Student
import com.example.mad_admin.viewmodel.MainViewModel
import java.util.UUID

class AddResultFragment : Fragment() {

    lateinit var binding : FragmentAddResultBinding
    private val viewModel : MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddResultBinding.inflate(layoutInflater)

        val user = arguments?.getParcelable<Student>("student")
        binding.btnAddResult.setOnClickListener {

            val result = Result(uid = UUID.randomUUID().toString(),
                title = binding.tvAddResultTitle.text.toString(),
                score = binding.tvAddResultMarksObtained.text.toString(),
                total = binding.tvAddResultTotalMarks.text.toString(),
                date = Utils.getCurrentDate(),
                time = Utils.getCurrentTime(),
                auther = user!!.uid)

            viewModel.uploadResult(requireContext(),result)
            requireActivity().supportFragmentManager.popBackStack()

        }




        return binding.root
    }






}