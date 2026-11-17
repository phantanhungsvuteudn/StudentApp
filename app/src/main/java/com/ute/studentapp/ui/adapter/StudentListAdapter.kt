// com/ute/studentapp/ui/adapter/StudentListAdapter.kt
package com.ute.studentapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ute.studentapp.databinding.ItemStudentBinding
import com.ute.studentapp.model.Student

class StudentListAdapter(
    private val onStudentClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : ListAdapter<Student, StudentListAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentViewHolder(
        private val binding: ItemStudentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.tvName.text = student.name
            binding.tvMssv.text = "MSSV: ${student.mssv}"
            binding.tvGpa.text = "GPA: ${student.gpa}"
            binding.tvAvatar.text = student.name.take(1)
            // mutate() để không ảnh hưởng các item khác dùng chung drawable
            binding.tvAvatar.background?.mutate()?.setTint(student.avatarColor)

            binding.root.setOnClickListener { onStudentClick(student) }
            binding.btnDelete.setOnClickListener { onDeleteClick(student) }
        }
    }

    // Bộ so sánh DiffUtil tính toán sai khác dòng
    class StudentDiffCallback : DiffUtil.ItemCallback<Student>() {
        // So sánh định danh ID (Primary Key)
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }

        // So sánh nội dung chi tiết của data class
        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }
}
