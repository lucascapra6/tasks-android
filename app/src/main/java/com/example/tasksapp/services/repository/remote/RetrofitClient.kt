package com.devmasterteam.tasks.service.repository.remote

import NetworkInfo
import android.content.Context
import android.widget.Toast
import com.example.tasksapp.exceptions.HttpExceptions
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitClient private constructor() {

    companion object {
        private lateinit var INSTANCE: Retrofit
        private var token: String = ""
        private var personKey: String = ""
        private lateinit var context: Context
        private lateinit var handleInvalidateSessionListener: () -> Unit
        // Configura o interceptor de logging para exibir os detalhes da requisição e resposta
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        private fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()

            // Adiciona o interceptor para adicionar os headers dinâmicos
            httpClient.addInterceptor(Interceptor { chain ->
                if (!NetworkInfo.isNetworkAvailable(context)) {
                    throw IOException("No internet connection")
                }

                val request = chain.request()
                    .newBuilder()
                    .addHeader("token", token)
                    .addHeader("personKey", personKey)
                    .build()

                chain.proceed(request)
            })

            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val response = chain.proceed(chain.request())

                    if (response.code == 403) {
                        handleInvalidateSessionListener()
                    }

                    return response
                }
            })

            // Adiciona o interceptor de logging
            httpClient.addInterceptor(loggingInterceptor)

            if (!::INSTANCE.isInitialized) {
                synchronized(RetrofitClient::class) {
                    INSTANCE = Retrofit.Builder()
                        .baseUrl("http://devmasterteam.com/CursoAndroidAPI/")
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
            return INSTANCE
        }

        fun <T> getService(serviceClass: Class<T>): T {
            return getRetrofitInstance().create(serviceClass)
        }

        fun addHeaders(tokenValue: String, personKeyValue: String) {
            token = tokenValue
            personKey = personKeyValue
        }
        fun init(context: Context) {
            this.context = context
        }
        fun setInvalidationSetListener(callback: () -> Unit) {
            handleInvalidateSessionListener = callback
        }
    }
}