package com.example.todokt.database

import kotlinx.coroutines.flow.Flow

class TodoItemRepository(private val itemDao: TodoItemDao) {

    fun getAllItemsStream(): List<TodoItemEntity> = itemDao.getAllItems()

    fun getItemStream(id: Int): Flow<TodoItemEntity?> = itemDao.getItem(id)

    suspend fun insertItem(item: TodoItemEntity) = itemDao.insert(item)

    suspend fun deleteItem(item: TodoItemEntity) = itemDao.delete(item)

    suspend fun updateItem(item: TodoItemEntity) = itemDao.update(item)

}