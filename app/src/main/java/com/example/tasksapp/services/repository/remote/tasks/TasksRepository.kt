package com.example.tasksapp.services.repository.remote.tasks

import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.services.infra.SharedPreferences.SharedPreferencesTasksHelper

class TasksRepository {
    private val taskService = RetrofitClient.getService(TasksServices::class.java)

    suspend fun fetchAllTasks(): List<TaskModel> {
        return taskService.getAllTasks()
    }
    suspend fun fetchNext7DaysTasks(): List<TaskModel> {
        return taskService.getNext7DaysTasks()
    }
    suspend fun fetchOverdueTasks(): List<TaskModel> {
        return taskService.getOverdueTasks()
    }
    suspend fun fetchTask(id: String): TaskModel {
        return taskService.getTaskById(id)
    }
    suspend fun createTasks(taskModel: TaskModel): Boolean {
        return taskService.createTask(taskModel)
    }
    suspend fun updateTask(taskModel: TaskModel): Boolean {
        return taskService.updateTask(taskModel)
    }
    suspend fun deleteTask(id: String): Boolean {
        return taskService.deleteTask(id)
    }
}