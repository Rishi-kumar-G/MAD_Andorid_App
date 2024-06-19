package com.example.mad_admin.Fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.mad_admin.Utils
import com.example.mad_admin.databinding.FragmentAddHomeWorkBinding
import com.example.mad_admin.models.Constants
import com.example.mad_admin.models.HomeWork
import com.example.mad_admin.viewmodel.MainViewModel
import com.gtappdevelopers.kotlingfgproject.ViewPagerAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.log


class AddHomeWorkFragment : Fragment() {


    lateinit var binding: FragmentAddHomeWorkBinding

    val viewModel:MainViewModel by viewModels()

    val openImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {

        val images = it.take(5)

        _imagesDate.value = images

        val adapter = ViewPagerAdapter(requireContext(), _imagesDate.value!!)
        binding.imageViewpager.adapter = adapter



    }
    private val _imagesDate = MutableStateFlow<List<Uri>?>(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddHomeWorkBinding.inflate(layoutInflater)



        Utils.setListAdapter(requireContext(), Constants.standards, binding.tvAddClass)
        Utils.setListAdapter(requireContext(), Constants.sections, binding.tvAddSection)
        Utils.setListAdapter(requireContext(), Constants.subjects, binding.tvAddSubjects)
        val user = Utils.getUser(requireContext())




        binding.btnAddHomeWork.setOnClickListener{
            val homeWork = HomeWork(
                uid = UUID.randomUUID().toString(),
                title = binding.tvAddTitle.text.toString(),
                desc = binding.tvAddDesc.text.toString(),
                auther = user.uid,
                date = Utils.getCurrentDate(),
                time = Utils.getCurrentTime(),
                standard = binding.tvAddClass.text.toString(),
                subject = binding.tvAddSubjects.text.toString(),
                section = binding.tvAddSection.text.toString(),
            )

            if (_imagesDate.value==null){
                viewModel.uploadHomeWork(requireContext(),homeWork)
            }
            else{
                viewModel.uploadImages(requireContext(), _imagesDate.value!!,homeWork)
            }



        }

        viewModel.apply {
            lifecycleScope.launch {
                homeWorkUploded.collect{
                    if (it){
                        clear()
                    }
                }
            }
        }

        binding.btnAddImage.setOnClickListener{
            Utils.openMultiImagePicker(requireActivity(),Constants.RequestCodeHomeWork)
        }



        onImageSelect()






        return binding.root
    }

    fun clear(){
        binding.tvAddDesc.setText("")
        binding.tvAddClass.setText("")
        binding.tvAddSection.setText("")
        binding.tvAddTitle.setText("")
        binding.tvAddSubjects.setText("")
        binding.imageViewpager.adapter = null
    }

    fun onImageSelect(){

        binding.btnAddImage.setOnClickListener {
            openImages.launch("image/*")
        }
    }




}