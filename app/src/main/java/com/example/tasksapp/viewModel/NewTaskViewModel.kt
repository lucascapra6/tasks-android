package com.example.tasksapp.viewModel

import android.app.Application
import com.example.tasksapp.models.Tasks.TaskModel

class NewTaskViewModel(application: Application): AbstractViewModel(application) {
    fun saveTask(newTask: TaskModel) {}
}