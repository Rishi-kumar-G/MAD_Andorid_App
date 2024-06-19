package com.example.mad_admin.Fragment

import ChatListAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_admin.R
import com.example.mad_admin.databinding.FragmentChatsBinding
import com.example.mad_admin.models.ChatUser
import com.example.mad_admin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class ChatsFragment : Fragment() {

    lateinit var binding: FragmentChatsBinding
    private val viewModel: MainViewModel by viewModels()
    lateinit var adapter : ChatListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(layoutInflater)

        adapter = ChatListAdapter()
        binding.rvChatList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChatList.adapter = adapter

//        viewModel.fetchChatList(uid)

        viewModel.apply {
            lifecycleScope.launch {
                fetchChatList().collect{
                    Log.d("rishi", "onCreateView: $it")
                    if(it==null || it == emptyArray<ChatUser>()){
                        binding.tvNoting.visibility = View.VISIBLE

                    }else{
                        binding.tvNoting.visibility = View.GONE
                    }
                    adapter.differ.submitList(it)
                    adapter.notifyDataSetChanged()
                }

            }
        }

        adapter.onItemClick={ user , pos ->
            loadChatFragmentWithBundle(user)

        }

        return binding.root
    }

    private fun loadChatFragmentWithBundle(user: ChatUser){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        val ChatsFragment = ChatViewFragment()
        val args = Bundle()
        args.putString("auther", user.uid)




        ChatsFragment.arguments = args
        transaction.replace(R.id.container, ChatsFragment)
        transaction.commit()
    }


}