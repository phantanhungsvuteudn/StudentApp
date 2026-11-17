// com/ute/studentapp/ui/StudentDetailFragment.kt
package com.ute.studentapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ute.studentapp.databinding.FragmentStudentDetailBinding
import com.ute.studentapp.model.Student
import com.ute.studentapp.viewmodel.StudentViewModel

class StudentDetailFragment : Fragment() {

    companion object {
        private const val ARG_STUDENT_ID = "STUDENT_ID"

        fun newInstance(studentId: String) = StudentDetailFragment().apply {
            arguments = bundleOf(ARG_STUDENT_ID to studentId)
        }
    }

    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    // Dùng chung ViewModel với màn hình danh sách
    private val viewModel: StudentViewModel by activityViewModels()

    private val studentId: String by lazy { requireArguments().getString(ARG_STUDENT_ID).orEmpty() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteStudent(studentId)
            parentFragmentManager.popBackStack()
        }

        viewModel.students.observe(viewLifecycleOwner) { list ->
            val student = list.firstOrNull { it.id == studentId }
            if (student != null) bind(student)
        }
    }

    private fun bind(student: Student) {
        binding.tvName.text = student.name
        binding.tvMssv.text = student.mssv
        binding.tvMajor.text = student.major
        binding.tvGpa.text = student.gpa.toString()
        binding.tvRank.text = rankOf(student.gpa)
        binding.tvAvatar.text = student.name.take(1)
        binding.tvAvatar.background?.mutate()?.setTint(student.avatarColor)
    }

    // Xếp loại học lực theo thang điểm 4.0
    private fun rankOf(gpa: Double): String = when {
        gpa >= 3.6 -> "Xuất sắc"
        gpa >= 3.2 -> "Giỏi"
        gpa >= 2.5 -> "Khá"
        gpa >= 2.0 -> "Trung bình"
        else -> "Yếu"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
