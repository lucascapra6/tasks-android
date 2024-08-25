package com.example.tasksapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tasksapp.R

abstract class AbstractViewModel(private val applicationContext: Application): AndroidViewModel(applicationContext) {
    fun stringfyResource(R: Int): String {
        return applicationContext.getString(R)
    }
}