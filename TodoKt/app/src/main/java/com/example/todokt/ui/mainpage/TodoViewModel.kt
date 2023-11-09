package com.example.todokt.ui.mainpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todokt.database.TodoItemEntity
import com.example.todokt.database.TodoItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(private val itemsRepository: TodoItemRepository) : ViewModel() {

    var description by mutableStateOf("")
        private set

    fun updateDescription(input: String) {
        description = input
    }

    var todoItemList = listOf<TodoItemEntity>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        updateList()
    }

    fun updateList() {
        coroutineScope.launch {
            todoItemList = itemsRepository.getAllItemsStream()
        }
    }

    suspend fun deleteTodoItem(todoItemEntity: TodoItemEntity){
        itemsRepository.deleteItem(todoItemEntity)
        updateList()
    }

    suspend fun addTodoItem(todoItemEntity: TodoItemEntity) {
        itemsRepository.insertItem(todoItemEntity)
        updateList()
    }

}