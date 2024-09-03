package com.example.tasksapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.ItemTaskBinding
import com.example.tasksapp.models.Tasks.TaskModel

class TaskAdapter(
    private var taskList: List<TaskModel>,
    private val onEditClick: (TaskModel) -> Unit,
    private val onDeleteClick: (TaskModel) -> Unit): RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, onEditClick, onDeleteClick)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    fun updateTasks(tasks: List<TaskModel>) {
        taskList = tasks
        notifyDataSetChanged()
    }
}

class TaskViewHolder(
    private val binding: ItemTaskBinding,
    private val onEditClick: (TaskModel) -> Unit,
    private val onDeleteClick: (TaskModel) -> Unit): RecyclerView.ViewHolder(binding.root) {
    fun bind(task: TaskModel) {
        binding.textViewTask.text = task.description

        binding.iconEdit.setOnClickListener {
            onEditClick(task)
        }

        binding.iconDelete.setOnClickListener {
            onDeleteClick(task)
        }
    }
}