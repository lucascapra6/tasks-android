package com.example.tasksapp.ui.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient

abstract class SessionManagerActivity: AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.setInvalidationSetListener { onSessionInvalid() }
    }
    private fun onSessionInvalid() {
        logout()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}