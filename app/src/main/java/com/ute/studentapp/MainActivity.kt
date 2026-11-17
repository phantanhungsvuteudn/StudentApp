// com/ute/studentapp/MainActivity.kt
package com.ute.studentapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentapp.databinding.ActivityMainBinding
import com.ute.studentapp.ui.StudentDetailFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận yêu cầu mở chi tiết từ StudentListFragment qua Fragment Result API
        supportFragmentManager.setFragmentResultListener(
            "STUDENT_DETAIL_REQ", this
        ) { _, bundle ->
            val id = bundle.getString("STUDENT_ID") ?: return@setFragmentResultListener
            openDetail(id)
        }
    }

    private fun openDetail(studentId: String) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, StudentDetailFragment.newInstance(studentId))
            .addToBackStack(null) // nút Back của máy sẽ quay về danh sách
            .commit()
    }
}
