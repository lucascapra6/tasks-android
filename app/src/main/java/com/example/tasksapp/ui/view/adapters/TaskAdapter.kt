package com.example.tasksapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksapp.databinding.ItemTaskBinding
import com.example.tasksapp.models.Tasks.TaskModel
import okhttp3.internal.concurrent.Task

class TaskAdapter(private var taskList: List<TaskModel>): RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
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

class TaskViewHolder(private val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(task: TaskModel) {
        binding.textViewTask.text = task.description
    }
}