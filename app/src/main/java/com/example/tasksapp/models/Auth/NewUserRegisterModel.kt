package com.example.tasksapp.models.Auth

data class NewUserRegisterModel (
    val name: String,
    val email: String,
    val password: String,
    val receiveNews: Boolean? = false
)