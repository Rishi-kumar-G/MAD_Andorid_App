import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.mad_admin.databinding.ViewStudentBinding

import com.example.mad_admin.models.Student


class StudentAdapter() :
    RecyclerView.Adapter<StudentAdapter.viewHolder>() {


    val diff = object : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diff)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        return viewHolder(
            ViewStudentBinding.inflate(
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

        val student = differ.currentList[position]
        holder.binding.apply {
            tvNameStd.text = student.name

        }
        if (student.status == "p"){
            holder.binding.cbStd.isChecked = true
            holder.binding.cbStd.isEnabled = true


        }
        holder.binding.cbStd.setOnClickListener {
            onItemClick
                ?.invoke(student, position,holder)
        }


    }

    var onItemClick: ((Student, Int,viewHolder) -> Unit)? = null

    class viewHolder(val binding: ViewStudentBinding) : RecyclerView.ViewHolder(binding.root)


}
