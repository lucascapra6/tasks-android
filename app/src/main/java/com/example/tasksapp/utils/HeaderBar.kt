package com.example.tasksapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class HeaderBar private constructor(
    private val context: AppCompatActivity,
    private val toolbar: Toolbar,
    private val insertBackButton: Boolean,
    onPressBackButton: () -> Unit
) {

    init {
        context.setSupportActionBar(toolbar)
        if (insertBackButton) {
            toolbar.setNavigationOnClickListener {
                onPressBackButton()
            }
            context.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            context.supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    // Classe Builder responsável por configurar o ActionBar
    class Builder(private val context: AppCompatActivity) {
        private var showActionBar: Boolean = false
        private var insertBackButton: Boolean = false
        private lateinit var onPressBackButton: () -> Unit
        fun insertBackButton(onPressBackButton: () -> Unit) = apply {
            this.onPressBackButton = onPressBackButton
            this.insertBackButton = true
        }

        fun build(toolbar: Toolbar): HeaderBar {
            return HeaderBar(context, toolbar, insertBackButton, onPressBackButton)
        }
    }
}
