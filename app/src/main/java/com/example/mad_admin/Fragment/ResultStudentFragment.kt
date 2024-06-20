package com.example.mad_admin.Fragment

import ResultsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_admin.AddResultFragment
import com.example.mad_admin.R
import com.example.mad_admin.databinding.FragmentResultStudentBinding
import com.example.mad_admin.models.Student
import com.example.mad_admin.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class ResultStudentFragment : Fragment() {

    lateinit var binding: FragmentResultStudentBinding
    private val viewModel : MainViewModel by viewModels()
    lateinit var adapter: ResultsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultStudentBinding.inflate(layoutInflater)

        val student = arguments?.getParcelable<Student>("student")
        val uid = student!!.uid
        adapter = ResultsAdapter()
        binding.rvResult.layoutManager = LinearLayoutManager(context)


        binding.rvResult.adapter = adapter
        viewModel.apply {
            lifecycleScope.launch {
                fetchStudentResult(uid).collect{
                    adapter.differ.submitList(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.btnAddResult.setOnClickListener{
            loadFragmentWithBundle(user = student)
        }


        binding.imgBack.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }


        return binding.root
    }

    private fun loadFragmentWithBundle(user:Student){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        val ChatsFragment = AddResultFragment()
        val args = Bundle()

        args.putParcelable("student", user)




        ChatsFragment.arguments = args
        transaction.replace(R.id.container, ChatsFragment).addToBackStack("result")

        transaction.commit()
    }


}