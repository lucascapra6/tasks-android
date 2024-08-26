package com.example.tasksapp.ui.view.activities

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasksapp.databinding.ActivityNewTaskFormBinding
import com.example.tasksapp.utils.HeaderBar

class NewTaskFormActivity: AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityNewTaskFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        HeaderBar.Builder(this).insertBackButton { finish() }.build(binding.toolbar)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}