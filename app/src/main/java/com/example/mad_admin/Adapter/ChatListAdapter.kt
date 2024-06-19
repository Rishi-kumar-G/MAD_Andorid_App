import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_admin.databinding.ViewChatBinding
import com.example.mad_admin.models.ChatUser
import com.example.mad_admin.models.Constants
import com.example.mad_admin.models.Users
import com.google.firebase.firestore.FirebaseFirestore




class ChatListAdapter() :
    RecyclerView.Adapter<ChatListAdapter.viewHolder>() {


    val diff = object : DiffUtil.ItemCallback<ChatUser>() {
        override fun areItemsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diff)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        return viewHolder(
            ViewChatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val person = differ.currentList[position]


        val uid = person.uid

        FirebaseFirestore.getInstance().collection(Constants.CollectionStudents).document(uid)
            .get().addOnSuccessListener {
            val user = it.toObject(Users::class.java)

            holder.binding.apply {
                tvName.text = user!!.name!!
                tvLastMsg.text = person.lastMsg
            }
            holder.binding.root.setOnClickListener{
                onItemClick
                    ?.invoke(person,position)
            }
        }


    }

    var onItemClick: ((ChatUser, Int) -> Unit)? = null

    class viewHolder(val binding: ViewChatBinding) : RecyclerView.ViewHolder(binding.root)


}
