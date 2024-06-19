package com.example.mad_admin.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mad_admin.Adapter.ViewPagerOnlineAdapter
import com.example.mad_admin.EditFragment
import com.example.mad_admin.R
import com.example.mad_admin.databinding.FragmentHWViewBinding
import com.example.mad_admin.models.HomeWork
import com.example.mad_admin.viewmodel.MainViewModel


class HW_ViewFragment : Fragment() {

    lateinit var binding:FragmentHWViewBinding
    private val mainViewModal:MainViewModel by viewModels()
    private var homeWork :HomeWork = HomeWork()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title")
        val homeWork = arguments?.getParcelable<HomeWork>("homeWork")



        Log.d("rishi2"," 232 "+homeWork.toString())
        if (homeWork != null){
        binding.tvHwSubject.text = homeWork.subject
        binding.tvHwTitle.text = title
        binding.tvHwDesc.text = homeWork.desc
        binding.tvHwDate.text = homeWork.date
        binding.tvHwAuther.text = "by:${homeWork.auther}"
            if (homeWork.urls != null) {
                binding.vpHomeworkViewpager.adapter =
                    ViewPagerOnlineAdapter(requireContext(), homeWork.urls!!)
            }else{
                binding.vpHomeworkViewpager.adapter =
                    ViewPagerOnlineAdapter(requireContext(), listOf("https://"))
            }
        }

        binding.btnHwBack.setOnClickListener{
            loadFragment(HomeFragment())
        }

        binding.btnHwEdit.setOnClickListener{
            if (homeWork != null) {
                onEditClick(homeWork)
            }
        }

        binding.btnHwDelete.setOnClickListener{
            if (homeWork != null) {
                mainViewModal.DeletehomeWorkWithImages(requireContext(),homeWork)
            }
            loadFragment(HomeFragment())
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHWViewBinding.inflate(layoutInflater)


        return binding.root
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    fun onEditClick(homeWork: HomeWork){

        val editFragment = EditFragment()
        val bundle = Bundle()

        bundle.putParcelable("homeWork",homeWork)
        editFragment.arguments = bundle
        loadFragment(editFragment)
//        findNavController().navigate(R.id.action_homeFragment_to_HW_ViewFragment2,bundle)


    }


}