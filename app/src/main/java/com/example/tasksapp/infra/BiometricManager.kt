package com.example.tasksapp.infra

import android.content.Context
import androidx.biometric.BiometricManager

class BiometricsUtils {
    companion object {

        fun verifyIsBiometricAvailable(context: Context) {
            val biometricManager = BiometricManager.from(context)
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    // O dispositivo não possui hardware biométrico
                    throw BiometricException.NoBiometricHardwareException
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    // O hardware biométrico não está disponível no momento
                    throw BiometricException.NoBiometricHardwareException
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    // O usuário ainda não cadastrou uma biometria
                    throw BiometricException.NoBiometricEnrolledException
                }
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    true
                }
                else -> {
                    // Lança uma exceção genérica para erros desconhecidos
                    throw BiometricException.UnknownBiometricException
                }
            }
        }
    }
}

sealed class BiometricException(message: String) : Exception(message) {

    data object NoBiometricHardwareException :
        BiometricException("Dispositivo não possui hardware biométrico")

    data object BiometricUnavailableException :
        BiometricException("Hardware biométrico está indisponível no momento")

    data object NoBiometricEnrolledException :
        BiometricException("Nenhuma biometria cadastrada no dispositivo")

    data object UnknownBiometricException :
        BiometricException("Erro desconhecido na autenticação biométrica")
}