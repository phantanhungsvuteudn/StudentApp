// com/ute/studentapp/model/Student.kt
package com.ute.studentapp.model

data class Student(
    val id: String,          // Khóa chính định danh duy nhất (UUID)
    val mssv: String,        // Mã số sinh viên UTE
    val name: String,        // Họ và tên
    val gpa: Double,         // Điểm tích lũy hệ 4.0
    val major: String,       // Chuyên ngành đào tạo
    val avatarColor: Int     // Mã màu hiển thị trên avatar
)
