package com.example.tasksapp.services.repository.remote.tasks

import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper

class TasksRepository {
    private val taskService = RetrofitClient.getService(TasksServices::class.java)

    suspend fun fetchAllTasks(): List<TaskModel> {
        return taskService.getAllTasks()
    }
    suspend fun createTasks(taskModel: TaskModel): Boolean {
        return taskService.createTask(taskModel)
    }
}