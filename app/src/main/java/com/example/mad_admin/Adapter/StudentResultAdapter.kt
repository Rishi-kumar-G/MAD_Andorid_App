import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_admin.databinding.ViewResultStudentBinding

import com.example.mad_admin.databinding.ViewStudentBinding

import com.example.mad_admin.models.Student


class StudentResultAdapter() :
    RecyclerView.Adapter<StudentResultAdapter.viewHolder>() {


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
            ViewResultStudentBinding.inflate(
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

        holder.binding.root.setOnClickListener {
            onItemClick
                ?.invoke(student, position,holder)
        }


    }

    var onItemClick: ((Student, Int,viewHolder) -> Unit)? = null

    class viewHolder(val binding: ViewResultStudentBinding) : RecyclerView.ViewHolder(binding.root)


}
