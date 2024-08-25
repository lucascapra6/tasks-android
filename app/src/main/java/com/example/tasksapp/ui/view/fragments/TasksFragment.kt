package com.example.tasksapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.databinding.FragmentTasksBinding
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.ui.view.adapters.TaskAdapter
import com.example.tasksapp.viewModel.TasksViewModel

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: TasksViewModel
    private var tasks: List<TaskModel> = listOf(TaskModel("", "", "", "", false))
    private lateinit var adapter: TaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(TasksViewModel::class.java)

        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val recyclerView = binding.recyclerViewTasks
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TaskAdapter(tasks)
        recyclerView.adapter = adapter

        fetchTasks()

        observe()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observe() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            tasks = it
            adapter.updateTasks(it)
        }
    }

    fun fetchTasks() {
        viewModel.fetchAllTasks()
    }
}