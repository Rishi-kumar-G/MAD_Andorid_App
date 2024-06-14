package com.example.mad_admin.Fragment

import HomeWorkAdapter
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_admin.R
import com.example.mad_admin.SettingsFragment
import com.example.mad_admin.Utils
import com.example.mad_admin.databinding.FragmentHomeBinding
import com.example.mad_admin.models.Constants
import com.example.mad_admin.models.HomeWork
import com.example.mad_admin.viewmodel.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val mainViewmodel : MainViewModel by viewModels()
    lateinit var adapter:HomeWorkAdapter
    private var _standard=MutableStateFlow<String>("1")
    private var _section=MutableStateFlow<String>("A")
    private var _date=MutableStateFlow<String>(Utils.getCurrentDate())

    private val _HomeWorkData = MutableStateFlow<ArrayList<HomeWork>>(ArrayList<HomeWork>())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.rvMainHomeWork.layoutManager = LinearLayoutManager(requireContext())



        binding.btnSettingHome.setOnClickListener{
            loadFragment(SettingsFragment())
        }
        binding.btnHomeDate.setText(Utils.getCurrentDate())

        adapter= HomeWorkAdapter()
        adapter.differ.submitList(_HomeWorkData.value)
        adapter.onItemClick ={ item, pos->

            onHomeWorkClick(item)
        }
        binding.rvMainHomeWork.adapter = adapter


        Utils.setListAdapter(requireContext(),Constants.standards,binding.tvHomeClass)
        binding.tvHomeClass.setText(Constants.standards[0])
        if (Utils.getUser(requireContext()).standard != null) {
            binding.tvHomeClass.setText(Utils.getUser(requireContext()).standard)
            _standard.value = Utils.getUser(requireContext()).standard.toString()

        }
        Utils.setListAdapter(requireContext(),Constants.sections,binding.tvHomeSection)
        if (Utils.getUser(requireContext()).section != null){
            binding.tvHomeSection.setText(Utils.getUser(requireContext()).section)
            _section.value = Utils.getUser(requireContext()).section.toString()
        }

        binding.tvHomeClass.setOnItemClickListener{parent, view, position, id ->
            _standard.value = binding.tvHomeClass.text.toString()
            setHomeWork(_standard.value,_section.value,_date.value)


        }

        binding.tvHomeSection.setOnItemClickListener { parent, view, position, id ->
            _section.value = binding.tvHomeSection.text.toString()
            setHomeWork(_standard.value,_section.value,_date.value)

        }





        binding.btnHomeDate.setOnClickListener{
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    // Handle the selected date here
                    var selectedDate:String
                    if(monthOfYear+1 <10){
                        selectedDate  = "$year-0${monthOfYear + 1}-$dayOfMonth"

                    }
                    if(dayOfMonth <10){
                        selectedDate  = "$year-${monthOfYear + 1}-0$dayOfMonth"

                    }

                    if(dayOfMonth <10 && monthOfYear+1 <10){
                        selectedDate  = "$year-0${monthOfYear + 1}-0$dayOfMonth"

                    }
                    else {
                      selectedDate  = "$year-${monthOfYear + 1}-$dayOfMonth"
                    }

                    _date.value = selectedDate

                    binding.btnHomeDate.setText(selectedDate)
//                    val data = mainViewmodel.fetchHomeWork(standard.value,section.value,selectedDate)
//                    val adapter = HomeWorkAdapter(requireContext(),data)
//                    binding.rvMainHomeWork.adapter = adapter
                    _date.update { selectedDate }
                    setHomeWork(_standard.value,_section.value,_date.value)


                },
                year, month, day)

            datePickerDialog.show()
        }

        setHomeWork(_standard.value,_section.value,_date.value)



        return binding.root
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setHomeWork(standard:String, section:String, date:String){
        Log.d("rishi","$standard $section $date")
        lifecycleScope.launch {
            mainViewmodel.fetchData(standard,section,date).collect{
               _HomeWorkData.value = it

                if (it.isEmpty()){
                    binding.tvMainNoting.visibility = View.VISIBLE
                    binding.rvMainHomeWork.visibility = View.GONE
                }
                else{
                    binding.tvMainNoting.visibility = View.GONE
                    binding.rvMainHomeWork.visibility = View.VISIBLE
                    adapter.differ.submitList(it)
                    adapter.notifyDataSetChanged()
                }

            }
        }



    }

    fun onHomeWorkClick(homeWork: HomeWork){
        mainViewmodel.setHomeWork(homeWork)
        val hwHW_ViewFragment = HW_ViewFragment()
        val bundle = Bundle()
        bundle.putString("title",homeWork.title)
        bundle.putParcelable("homeWork",homeWork)
        hwHW_ViewFragment.arguments = bundle
        Log.d("rishi",homeWork.toString())
        loadFragment(hwHW_ViewFragment)



    }




}