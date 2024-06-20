import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mad_admin.databinding.ViewResultBinding
import com.example.mad_admin.databinding.ViewResultStudentBinding

import com.example.mad_admin.databinding.ViewStudentBinding
import com.example.mad_admin.models.Result

import com.example.mad_admin.models.Student
import kotlin.math.round


class ResultsAdapter() :
    RecyclerView.Adapter<ResultsAdapter.viewHolder>() {


    val diff = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diff)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        return viewHolder(
            ViewResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val result = differ.currentList[position]
        holder.binding.apply {
            tvTitle.setText(result.title)
            val percent = (result.score!!.toInt()*1.0/result.total!!.toFloat())*100
            tvMarks.setText("${result.score}/${result.total}")
            tvPercent.setText("${round(percent)}%")

        }

        holder.binding.root.setOnClickListener {
            onItemClick
                ?.invoke(result, position,holder)
        }


    }

    var onItemClick: ((Result, Int,viewHolder) -> Unit)? = null

    class viewHolder(val binding: ViewResultBinding) : RecyclerView.ViewHolder(binding.root)


}
