package com.example.tasksapp.services.repository


import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.services.interfaces.ApiListener
import com.example.tasksapp.services.models.Auth.LoginModel
import com.example.tasksapp.services.models.Auth.UserModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository: BaseRepository() {
    private val remoteService: AuthApiService = RetrofitClient.getService(AuthApiService::class.java)

    fun login(email: String, password: String, listener: ApiListener<UserModel>) {
            val loginData = LoginModel(email, password)
            val call = remoteService.login(loginData)
            enqueueCall(call, listener)
    }
}
