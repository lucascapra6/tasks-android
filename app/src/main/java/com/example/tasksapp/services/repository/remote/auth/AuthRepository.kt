package com.example.tasksapp.services.repository.remote.auth


import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.services.interfaces.ApiListener
import com.example.tasksapp.models.Auth.LoginModel
import com.example.tasksapp.models.Auth.NewUserRegisterModel
import com.example.tasksapp.models.Auth.UserModel
import com.example.tasksapp.services.repository.BaseRepository

class AuthRepository: BaseRepository() {
    private val remoteService: AuthApiService = RetrofitClient.getService(AuthApiService::class.java)

    fun login(email: String, password: String, listener: ApiListener<UserModel>) {
            val loginData = LoginModel(email, password)
            val call = remoteService.login(loginData)
            enqueueCall(call, listener)
    }

    fun register(newUser: NewUserRegisterModel, listener: ApiListener<UserModel>) {
        val call = remoteService.register(newUser)
        enqueueCall(call, listener)
    }
}
