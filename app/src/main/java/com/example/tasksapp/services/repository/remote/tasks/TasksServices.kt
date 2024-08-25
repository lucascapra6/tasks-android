package com.example.tasksapp.services.repository.remote.tasks
import com.example.tasksapp.models.Tasks.TaskModel
import retrofit2.http.GET
import retrofit2.http.Path
interface TasksServices {
    @GET("Task")
    suspend fun getAllTasks(): List<TaskModel>

    @GET("Task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): TaskModel
}
