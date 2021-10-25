package com.ivamotelo.applistadetarefas

import android.app.Application
import com.ivamotelo.applistadetarefas.data.AppDatabase
import com.ivamotelo.applistadetarefas.data.TaskRepository


class App: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}