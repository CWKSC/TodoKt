package com.example.todokt

import android.app.Application
import com.example.todokt.database.TodoDatabase
import com.example.todokt.database.TodoItemRepository

class MyApplication : Application() {

    val database by lazy { TodoDatabase.getDatabase(this) }

    val todoItemRepository by lazy { TodoItemRepository(database.getTodoItemDao()) }

    override fun onCreate() {
        super.onCreate()
        // todoItemRepository = TodoItemRepository(TodoDatabase.getDatabase(this).getTodoItemDao())
    }

}
