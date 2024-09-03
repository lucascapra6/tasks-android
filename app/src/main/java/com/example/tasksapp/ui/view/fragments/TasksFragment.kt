package com.example.tasksapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasksapp.R
import com.example.tasksapp.databinding.FragmentTasksBinding
import com.example.tasksapp.models.Tasks.TaskModel
import com.example.tasksapp.ui.view.adapters.TaskAdapter
import com.example.tasksapp.viewModel.TasksViewModel

class TasksFragment : Fragment(), OnClickListener {

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
        adapter = TaskAdapter(
            taskList = tasks,
            onEditClick = { task ->
                onEditClick(task)
            },
            onDeleteClick = { task ->
                showDeleteConfirmationDialog(task)
            }
        )
        recyclerView.adapter = adapter
        setListeners()

        observe()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchTasks()
    }
    private fun observe() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            if(it.success) {
                handleHideError()
                tasks = it.data!!
                adapter.updateTasks(it.data)
            } else {
                handleShowError()
            }
        }

        viewModel.loadingTasks.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
        }
    }

    private fun fetchTasks() {
        viewModel.fetchAllTasks()
    }

    private fun handleShowError() {
        binding.errorLayout.visibility = View.VISIBLE
        binding.recyclerViewTasks.visibility = View.GONE
    }

    private fun handleHideError() {
        binding.errorLayout.visibility = View.GONE
        binding.recyclerViewTasks.visibility = View.VISIBLE
    }

    private fun setListeners() {
        binding.retryButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.retryButton -> {
                fetchTasks()
            }
        }
    }

    private fun onEditClick(task: TaskModel) {
        Toast.makeText(requireContext(), "Edit ${task.description}", Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteClick(task: TaskModel) {
        viewModel.deleteTask(task.id)
    }

    private fun showDeleteConfirmationDialog(task: TaskModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.task_delete_confirm_title)
        builder.setMessage(R.string.task_delete_confirm_description)

        builder.setPositiveButton(R.string.yes) { dialog, _ ->
            viewModel.deleteTask(task.id)
            dialog.dismiss()
            fetchTasks()
        }

        builder.setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }
}