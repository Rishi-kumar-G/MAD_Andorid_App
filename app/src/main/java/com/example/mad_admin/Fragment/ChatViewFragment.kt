package com.example.mad_admin.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad_admin.Adapter.ChatAdapter
import com.example.mad_admin.R
import com.example.mad_admin.Utils
import com.example.mad_admin.databinding.FragmentChatViewBinding
import com.example.mad_admin.models.ChatUser
import com.example.mad_admin.models.Constants
import com.example.mad_admin.models.HomeWork
import com.example.mad_admin.viewmodel.MainViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import com.predator.mad_admin.models.Messege

class ChatViewFragment : Fragment() {

    lateinit var binding: FragmentChatViewBinding
    lateinit var recipient_uid:String
    private val viewModel: MainViewModel by viewModels()
    var adminName = ""
    var homework: HomeWork? = null
    var sender_uid = ""
    var MessegesData = ArrayList<Messege>()
    lateinit var adapter : ChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatViewBinding.inflate(layoutInflater)

        sender_uid = Utils.getUser(requireContext()).uid
        recipient_uid = arguments?.getString("auther").toString()



        homework = arguments?.getParcelable<HomeWork>("hw")

        viewModel.fetchStudent(recipient_uid)

        viewModel.apply {
            lifecycleScope.launch {
                _AdminName.collect{
                    adminName = it
                    binding.tvRecipentName.text = adminName
                }
            }
        }

        if (homework==null){
            binding.viewAbout.visibility = View.GONE
        }else{
            binding.tvHwAbout.text = homework!!.title
        }

        binding.btnSend.setOnClickListener{
            var messege = Messege(
                date = Utils.getCurrentDate(),
                time = Utils.getCurrentTime(),
                msg = binding.tvMsg.text.toString(),
                sender_uid = Utils.getUser(requireContext()).uid,
                reciver_uid = recipient_uid,
            )
            if (homework!=null){
                messege.refference = homework!!.uid
            }
            sendMessege(requireContext(),messege)

            binding.tvMsg.setText("")
            binding.rvChat.scrollToPosition(MessegesData.size-1)




        }
        adapter = ChatAdapter()

        val layoutManager = LinearLayoutManager(context)


        binding.rvChat.layoutManager = layoutManager

        binding.rvChat.adapter = adapter

        FirebaseFirestore.getInstance().collection(Constants.CollectionMessege).document(recipient_uid+sender_uid).collection(Constants.CollectionMessege).get().addOnSuccessListener {
            Utils.showToast(requireContext(),recipient_uid+sender_uid)
            Log.d("rishi",recipient_uid+" \n"+sender_uid)

            for (doc in it){
                val messege = doc.toObject<Messege>()
                MessegesData.add(messege)
            }
            adapter.differ.submitList(MessegesData)
        }

        return binding.root
    }

    fun sendMessege(context: Context, messege: Messege){




        viewModel.getFireStoreInstance().collection(Constants.CollectionMessege).document(recipient_uid+sender_uid).collection(Constants.CollectionMessege).document().set(messege).addOnSuccessListener{
            Utils.showToast(context,"Messege Sent")
            MessegesData.add(messege)
            adapter.notifyItemInserted(MessegesData.size)
        }


    }


}