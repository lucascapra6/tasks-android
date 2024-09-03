package com.example.tasksapp.services.repository.remote.tasks
import com.example.tasksapp.models.Tasks.TaskModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TasksServices {
    @GET("Task")
    suspend fun getAllTasks(): List<TaskModel>

    @GET("Task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): TaskModel

    @POST("Task")
    suspend fun createTask(
        @Body task: TaskModel
    ): Boolean

    @HTTP(method = "DELETE", path = "Task", hasBody = true)
    @FormUrlEncoded
    suspend fun deleteTask(@Field("Id") id: String): Boolean
}
