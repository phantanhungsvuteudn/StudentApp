// com/ute/studentapp/viewmodel/StudentViewModel.kt
package com.ute.studentapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ute.studentapp.model.Student

class StudentViewModel : ViewModel() {

    // Encapsulation: biến MutableLiveData nội bộ, chỉ công khai LiveData chỉ đọc ra ngoài
    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> get() = _students

    init {
        // Khởi tạo danh sách sinh viên mẫu ban đầu
        _students.value = listOf(
            Student("1", "20110001", "Nguyễn Văn An", 3.65, "Công Nghệ Thông Tin", 0xFF059669.toInt()),
            Student("2", "20110002", "Trần Thị Mai", 3.82, "Kỹ Thuật Phần Mềm", 0xFF0284C7.toInt()),
            Student("3", "20110003", "Lê Hoàng Phúc", 3.20, "An Toàn Thông Tin", 0xFFD97706.toInt())
        )
    }

    fun addStudent(student: Student) {
        val currentList = _students.value?.toMutableList() ?: mutableListOf()
        currentList.add(0, student) // Chèn vào đầu danh sách
        _students.value = currentList
    }

    fun deleteStudent(studentId: String) {
        val currentList = _students.value?.toMutableList() ?: return
        val filtered = currentList.filterNot { it.id == studentId }
        _students.value = filtered
    }
}
