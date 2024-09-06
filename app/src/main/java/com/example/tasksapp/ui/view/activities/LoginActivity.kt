package com.example.tasksapp.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
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
                showBiometricPrompt()
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

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                // Erro ao autenticar
                Toast.makeText(applicationContext, "Erro de autenticação: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Sucesso na autenticação
                Toast.makeText(applicationContext, "Autenticação bem-sucedida", Toast.LENGTH_SHORT).show()
                startMainActivity()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Falha ao autenticar
                Toast.makeText(applicationContext, "Falha na autenticação", Toast.LENGTH_SHORT).show()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação Biométrica")
            .setSubtitle("Use sua impressão digital para acessar")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
