import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_admin.R
import com.example.mad_admin.databinding.ViewHomeworkBinding
import com.example.mad_admin.models.HomeWork
import com.squareup.picasso.Picasso

class HomeWorkAdapter() :
    RecyclerView.Adapter<HomeWorkAdapter.viewHolder>() {




    val diff = object : DiffUtil.ItemCallback<HomeWork>(){
        override fun areItemsTheSame(oldItem: HomeWork, newItem: HomeWork): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: HomeWork, newItem: HomeWork): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diff)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        return viewHolder(ViewHomeworkBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val homeWork = differ.currentList[position]
        holder.binding.apply {
            tvVhTitle.text = homeWork.title
            tvVhSubject.text = homeWork.subject
            tvVhDate.text = homeWork.date

        }
        holder.binding.root.setOnClickListener{
            onItemClick
            ?.invoke(homeWork,position)
        }



        Picasso.get().load(homeWork.urls?.get(0)).placeholder(R.drawable.knowledge).into(holder.binding.imgVhHwImage)

    }

    var onItemClick: ((HomeWork, Int) -> Unit)? = null

    class viewHolder(val binding: ViewHomeworkBinding): RecyclerView.ViewHolder(binding.root)


}
