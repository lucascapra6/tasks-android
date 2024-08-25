package com.example.tasksapp.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import com.example.tasksapp.R
import com.example.tasksapp.databinding.ActivityRegisterBinding
import com.example.tasksapp.models.Auth.NewUserRegisterModel
import com.example.tasksapp.models.Auth.UserModel
import com.example.tasksapp.models.ValidateApiModel
import com.example.tasksapp.viewModel.RegisterViewModel

class RegisterActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Aqui define o comportamento personalizado ao pressionar o botao de voltar do celular"
                //Toast.makeText(application,  "backhandler fisico pressionado", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
        viewModel = RegisterViewModel(application)
        setListeners()
        observe()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private fun observe() {
        viewModel.createdUserSuccess.observe(this) {
            if(it.success) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.buttonRegister -> {
                val name = binding.editTextName.text.toString()
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()
                val newUser = NewUserRegisterModel(name, email, password)
                viewModel.registerUser(newUser)
            }
        }
    }
}