package com.app.ubersnap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.ubersnap.data.Task
import com.app.ubersnap.data.TodoDao
import com.app.ubersnap.data.TodoDatabase
import com.app.ubersnap.repository.TaskRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao: TodoDao
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val todoDatabase = TodoDatabase.getDatabase(application)
        todoDao = todoDatabase.todoDao()
        repository = TaskRepository(todoDao)
        allTasks = repository.allTasks.asLiveData()
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteCompleted() = viewModelScope.launch {
        repository.deleteCompleted()
    }

    fun deletePendingTasks() = viewModelScope.launch {
        repository.deletePendingTasks()
    }
}
