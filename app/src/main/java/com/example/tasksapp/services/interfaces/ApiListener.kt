package com.example.tasksapp.services.interfaces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ApiListener<T> {
    fun onSuccess(response: T) {

    }

    fun onFailure(t: Throwable) {

    }
}