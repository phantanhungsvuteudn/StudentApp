// com/ute/studentapp/ui/StudentListFragment.kt
package com.ute.studentapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.ute.studentapp.databinding.FragmentStudentListBinding
import com.ute.studentapp.model.Student
import com.ute.studentapp.ui.adapter.StudentListAdapter
import com.ute.studentapp.viewmodel.StudentViewModel
import java.util.UUID

class StudentListFragment : Fragment() {

    // Chuẩn Google: quản lý vòng đời kép với Backing Property chống rò rỉ bộ nhớ
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!

    // Chia sẻ ViewModel chung với Activity chủ
    private val viewModel: StudentViewModel by activityViewModels()

    private lateinit var studentAdapter: StudentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentListAdapter(
            onStudentClick = { student ->
                // Gửi dữ liệu qua Fragment Result API
                setFragmentResult(
                    "STUDENT_DETAIL_REQ",
                    bundleOf("STUDENT_ID" to student.id)
                )
            },
            onDeleteClick = { student ->
                viewModel.deleteStudent(student.id)
            }
        )

        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = studentAdapter
            setHasFixedSize(true) // Tối ưu hiệu năng nếu kích thước mỗi item cố định
        }
    }

    private fun observeViewModel() {
        // Quan sát LiveData với viewLifecycleOwner để đồng bộ vòng đời View của Fragment
        viewModel.students.observe(viewLifecycleOwner) { list ->
            studentAdapter.submitList(list) // DiffUtil tự động tính toán trên luồng nền!
            binding.layoutEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            // Thêm sinh viên mẫu mới
            val newStudent = Student(
                id = UUID.randomUUID().toString(),
                mssv = "2011" + (1000..9999).random(),
                name = "Sinh Viên Mới",
                gpa = 3.50,
                major = "Công Nghệ Thông Tin",
                avatarColor = 0xFF7C3AED.toInt()
            )
            viewModel.addStudent(newStudent)
            binding.rvStudents.scrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Bắt buộc giải phóng ViewBinding để tránh rò rỉ bộ nhớ (Memory Leak)
        _binding = null
    }
}
