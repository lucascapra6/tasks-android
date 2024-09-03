package com.example.tasksapp.ui.view.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasksapp.R
import com.example.tasksapp.databinding.ActivityNewTaskFormBinding
import com.example.tasksapp.models.Priority.PriorityModel
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.utils.HeaderBar
import com.example.tasksapp.viewModel.NewTaskViewModel
import java.util.Calendar

class NewTaskFormActivity: AppCompatActivity(), OnClickListener, DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityNewTaskFormBinding
    private lateinit var viewModel: NewTaskViewModel
    private var isDataSelected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        HeaderBar.Builder(this).insertBackButton { finish() }.build(binding.toolbar)
        binding.buttonSaveTask.setDisable()
        viewModel = NewTaskViewModel(application)
        setupPrioritySpinner()
        setListeners()
        observe()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.buttonSelectDate -> {
                openDatePicker()
            }
            R.id.buttonSaveTask -> {
                saveTask()
            }
        }
    }

    private fun observe() {
        viewModel.taskResult.observe(this) {
            if(it.success) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isSaveTaskLoading.observe(this) {
            if(it) {
                binding.buttonSaveTask.setLoading()
            } else {
                binding.buttonSaveTask.setDefault()
            }
        }
    }

    private fun saveTask() {
        val description = binding.editTextDescription.text.toString()
        val selectedPriority = getPriorityIdByIndex(binding.spinnerPriority.selectedItemPosition).id.toString()
        val selectedDate = binding.buttonSelectDate.text.toString()
        val taskStatus = binding.checkBoxComplete.isChecked

        val task = TaskModel("0", selectedPriority, description, selectedDate, taskStatus )

        viewModel.saveTask(task)
    }
    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, this, year, month, day
        )
        datePickerDialog.show()
    }

    private fun setupPrioritySpinner() {
        val priorityOptions = arrayOf("Baixa", "Média", "Alta")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter
    }

    private fun getPriorityIdByIndex(index: Int): PriorityModel {
        val priorities = listOf<PriorityModel>(
            PriorityModel(1, "Low"),
            PriorityModel(2, "Medium"),
            PriorityModel(3, "High")
        )

        return priorities[index]
    }

    private fun setListeners() {
        binding.buttonSelectDate.setOnClickListener(this)
        binding.buttonSaveTask.setOnClickListener(this)
        binding.editTextDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkFormValidity()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun checkFormValidity() {
        val hasDescription = binding.editTextDescription.text.isNotEmpty()
        if(hasDescription && isDataSelected) binding.buttonSaveTask.setDefault()
        else binding.buttonSaveTask.setDisable()
    }

    override fun onDateSet(v: DatePicker?, year: Int, month: Int, day: Int) {
        binding.buttonSelectDate.text = "$day/${month}/$year"
        isDataSelected = true
        checkFormValidity()
    }
}