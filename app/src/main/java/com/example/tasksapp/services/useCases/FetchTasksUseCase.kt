package com.example.tasksapp.services.useCases

import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.services.repository.remote.tasks.TasksRepository
import com.example.tasksapp.utils.Constants

class FetchTasksUseCase(private val repository: TasksRepository) {

    suspend operator fun invoke(taskArgument: Int): Result<List<TaskModel>> {
        return try {
            val response = when (taskArgument) {
                Constants.TaskFilterValues.ALL_TASKS -> repository.fetchAllTasks()
                Constants.TaskFilterValues.NEXT_7_DAYS -> repository.fetchNext7DaysTasks()
                Constants.TaskFilterValues.OVERDUE -> repository.fetchOverdueTasks()
                else -> emptyList() // Padrão de segurança
            }
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}