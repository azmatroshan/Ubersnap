package com.app.ubersnap.repository

import com.app.ubersnap.data.Task
import com.app.ubersnap.data.TodoDao
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val todoDao: TodoDao) {

    val allTasks: Flow<List<Task>> = todoDao.getAllTasks()

    suspend fun insertTask(task: Task) {
        todoDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        todoDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        todoDao.deleteTask(task)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    suspend fun deleteCompleted() {
        todoDao.deleteCompleted()
    }

    suspend fun deletePendingTasks() {
        todoDao.deletePendingTasks()
    }
}
