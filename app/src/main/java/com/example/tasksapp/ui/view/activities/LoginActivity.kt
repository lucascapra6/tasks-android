package com.example.tasksapp.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devmasterteam.tasks.service.repository.remote.RetrofitClient
import com.example.tasksapp.R
import com.example.tasksapp.databinding.ActivityLoginBinding
import com.example.tasksapp.viewModel.LoginViewModel
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LoginViewModel(application)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RetrofitClient.init(application)
        setEventListeners()

        //verifica se tem usuario logado
        viewModel.handleLoggedSession()

        observe()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.buttonLogin -> {
                val email = binding.editTextID.text.toString()
                val password = binding.editTextPassword.text.toString()

                viewModel.doLogin(email, password)
            }
            R.id.registerUserButton -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun observe() {
        viewModel.loginResult.observe(this) {
            if (it.success) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                startMainActivity()
            } else {
                // Exibir uma mensagem de erro
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isUserLogged.observe(this) {
            if(it) {
                startMainActivity()
            }
        }
    }

    fun setEventListeners() {
        binding.buttonLogin.setOnClickListener(this)
        binding.registerUserButton.setOnClickListener(this)
    }

    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
