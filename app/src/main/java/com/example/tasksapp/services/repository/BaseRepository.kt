package com.example.tasksapp.services.repository

import com.example.tasksapp.services.interfaces.ApiListener
import com.example.tasksapp.services.models.Auth.UserModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseRepository {
    fun handleApiMessage(message: String): String {
        val gson = Gson()
        return gson.fromJson(message, String::class.java)
    }

    fun <T> enqueueCall(call: Call<T>, listener: ApiListener<T>) {
        call.enqueue(object: Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.code() == 200) {
                    // Passa a resposta para o listener fornecido
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    val errorMessage = response.errorBody()?.string()
                        ?.let { handleApiMessage(it) }
                        ?: "Erro desconhecido"
                    val exception = Exception(errorMessage)
                    listener.onFailure( exception )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                // Passa o erro para o listener fornecido
                listener.onFailure(t)
            }
        })
    }
}