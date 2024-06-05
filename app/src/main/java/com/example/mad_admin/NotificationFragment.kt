package com.example.mad_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mad_admin.databinding.FragmentNotificationBinding
import com.example.mad_admin.models.Constants
import com.example.mad_admin.models.Notification
import com.example.mad_admin.viewmodel.AuthViewModal
import com.example.mad_admin.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID


class NotificationFragment : Fragment() {

    lateinit var binding:FragmentNotificationBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationBinding.inflate(layoutInflater)

        Utils.setListAdapter(requireContext(),Constants.standards,binding.tvNotiClass)

        Utils.setListAdapter(requireContext(),Constants.sections,binding.tvNotiSection)

        binding.btnNotiPush.setOnClickListener{
            val notification = Notification(uid=UUID.randomUUID().toString(),
                title = binding.tvNotiTitle.text.toString(),
                body = binding.tvNotiDesc.text.toString(),
                date = Utils.getCurrentDate(),
                time = Utils.getCurrentTime(),
                section = binding.tvNotiSection.text.toString(),
                standard = binding.tvNotiClass.text.toString())

            viewModel.uploadNotification(requireContext(),notification)
        }

        viewModel.apply {
            lifecycleScope.launch {
                Notificationuploded.collect{
                    if(it){
                        binding.tvNotiTitle.text.clear()
                        binding.tvNotiDesc.text.clear()
                        binding.tvNotiClass.text.clear()
                        binding.tvNotiSection.text.clear()
                    }
                }
            }
        }



        // Inflate the layout for this fragment
        return binding.root
    }


}