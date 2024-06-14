package com.example.mad_admin.Fragment

import StudentAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_admin.Utils
import com.example.mad_admin.databinding.FragmentStudentsBinding
import com.example.mad_admin.models.Attendence
import com.example.mad_admin.models.Student
import com.example.mad_admin.viewmodel.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class StudentsFragment : Fragment() {

    lateinit var binding:FragmentStudentsBinding
    private val viewModel : MainViewModel by viewModels()
    lateinit var adapter: StudentAdapter
    private val _Students = MutableStateFlow(ArrayList<Student>())
    var Students = _Students.value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStudentsBinding.inflate(layoutInflater)


        val standard = Utils.getUser(requireContext()).standard.toString()
        val section = Utils.getUser(requireContext()).section.toString()


        binding.tvStandard.text = "$standard$section"
        adapter = StudentAdapter()
        binding.rvStudents.layoutManager = LinearLayoutManager(requireContext())



        binding.rvStudents.adapter = adapter
        setStudents(standard,section)
        adapter.onItemClick={item,pos,view->


            onCheckClick(item,pos,view)
        }

        binding.btnUpload.setOnClickListener{
            viewModel.uploadAttendence(requireContext(),Students)
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setStudents(standard:String, section:String){
        lifecycleScope.launch {
            viewModel.fetchStudents(standard,section).collect{

                _Students.value = it
                Students = it
                if (it.isEmpty()){
                    binding.tvNoting.visibility = View.VISIBLE

                }
                else{
                    binding.tvNoting.visibility = View.GONE

                    adapter.differ.submitList(it)
                    adapter.notifyDataSetChanged()
                }

            }
        }



    }

    fun onCheckClick(item: Student, pos: Int, view: StudentAdapter.viewHolder) {
        val status:String
        status = if (view.binding.cbStd.isChecked()){
            "p"
        }else{
            "a"
        }
        Students[pos].status = status
        Log.d("rishi",status)



    }




}