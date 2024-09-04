package com.example.tasksapp.ui.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tasksapp.R
import com.example.tasksapp.databinding.ProgressButtonBinding

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    private var label: String? = null
    private var loadingLabel: String? = null

    private val binding = ProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private var state: ProgressButtonState = ProgressButtonState.Default
        set(value) {
            field = value
            refreshState()
        }
    init {
        setLayout(attrs)
        refreshState()
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.ProgressButton)
            setBackgroundResource(R.drawable.progress_button_background)

            val labelResId = attributes.getResourceId(R.styleable.ProgressButton_button_label, 0)
            if(labelResId != 0) label = context.getString(labelResId)

            val labelLoadingResId = attributes.getResourceId(R.styleable.ProgressButton_button_loading_label, 0)
            if(labelLoadingResId != 0) loadingLabel = context.getString(labelLoadingResId)

            attributes.recycle()
        }
    }

    private fun refreshState() {
        isEnabled = state.isEnabled
        isClickable = state.isEnabled
        refreshDrawableState()

//        binding.textTitle.run {
//            isEnabled = state.isEnabled
//            isClickable = state.isEnabled
//        }

        binding.progressButton.visibility = state.progressVisibility

        when(state) {
            ProgressButtonState.Default -> {
                binding.textTitle.text = label
            }
            ProgressButtonState.Loading -> {
                binding.textTitle.text = loadingLabel
            }
            ProgressButtonState.Disabled -> {
                binding.textTitle.text = label
            }
        }
    }

    fun setLoading() {
        state = ProgressButtonState.Loading
    }

    fun setDefault() {
        state = ProgressButtonState.Default
    }
    fun setDisable() {
        state = ProgressButtonState.Disabled
    }

    fun setLabel(text: String) {
        label = text
    }
}

enum class ProgressButtonState(val isEnabled: Boolean, val progressVisibility: Int) {
    Default(isEnabled = true, progressVisibility = View.GONE),
    Loading(isEnabled = false, progressVisibility = View.VISIBLE),
    Disabled(isEnabled = false, progressVisibility = View.GONE)
}