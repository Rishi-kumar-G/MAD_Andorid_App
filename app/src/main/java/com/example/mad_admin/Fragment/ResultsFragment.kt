package com.example.mad_admin.Fragment

import StudentResultAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_admin.R
import com.example.mad_admin.Utils
import com.example.mad_admin.databinding.FragmentResultsBinding
import com.example.mad_admin.models.Student
import com.example.mad_admin.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class ResultsFragment : Fragment() {

    lateinit var binding : FragmentResultsBinding
    private val viewModel : MainViewModel by viewModels()
    lateinit var adapter : StudentResultAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultsBinding.inflate(layoutInflater)

        adapter = StudentResultAdapter()

        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())

        binding.rvStudents.adapter = adapter

        viewModel.apply {
            lifecycleScope.launch {
                fetchStudents(Utils.getUser(requireContext()).standard!!, Utils.getUser(requireContext()).section!!).collect{
                    adapter.differ.submitList(it)
                    adapter.notifyDataSetChanged()

                }
            }
        }

        adapter.onItemClick={ student,pos,type ->
            loadFragmentWithBundle(student)
        }

        binding.imgRessultBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }



        return binding.root
    }

    private fun loadFragmentWithBundle(user:Student){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        val ChatsFragment = ResultStudentFragment()
        val args = Bundle()

        args.putParcelable("student", user)




        ChatsFragment.arguments = args
        transaction.replace(R.id.container, ChatsFragment).addToBackStack("")
        transaction.commit()
    }


}