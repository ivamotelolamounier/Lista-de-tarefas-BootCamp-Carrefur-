package com.ivamotelo.applistadetarefas.data

import com.ivamotelo.applistadetarefas.data.model.Task

class TaskRepository(private val dao: TaskDao) {

    suspend fun insert(task: Task) = dao.insert(task)

    suspend fun getAll(): List<Task> = dao.getAll()

    suspend fun getTask(taskId: Int) = dao.getTask(taskId)

    suspend fun delete(task: Task) = dao.delete(task)

    suspend fun update(task: Task) = dao.update(task)
}