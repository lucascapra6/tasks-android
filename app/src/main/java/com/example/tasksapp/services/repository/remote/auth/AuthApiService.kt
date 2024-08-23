package com.example.tasksapp.services.repository.remote.auth

import com.example.tasksapp.services.models.Auth.LoginModel
import com.example.tasksapp.services.models.Auth.UserModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("Authentication/Login")
    fun login(@Body request: LoginModel): Call<UserModel>
}