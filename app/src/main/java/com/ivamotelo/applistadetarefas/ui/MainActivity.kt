package com.ivamotelo.applistadetarefas.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ivamotelo.applistadetarefas.App
import com.ivamotelo.applistadetarefas.databinding.ActivityMainBinding
import com.ivamotelo.applistadetarefas.ui.adapter.TaskListAdapter
import com.ivamotelo.applistadetarefas.ui.viewmodel.MainViewModel
import com.ivamotelo.applistadetarefas.ui.viewmodel.MainViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()
        initObservers()

        initActions()
    }

    private fun initActions() {
        binding.fabAddTask.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
            updateList()
        }

        adapter.listenerDelete = {
            mainViewModel.delete(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList(){
        mainViewModel.getAll()
    }

    private fun initObservers(){
        mainViewModel.listResponse.observe(this, {
            binding.emptyState.emptyState.isVisible = it.isEmpty()
            adapter.submitList(it)
        })
    }

    companion object{
        const val CREATE_NEW_TASK = 1000
    }
}