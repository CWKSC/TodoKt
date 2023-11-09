package com.example.todokt.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity
data class TodoItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dateMillis: Long = 0,
    val hour: Int = 0,
    val minute: Int = 0,
    val is24hour: Boolean = true,
    val description: String = ""
) {

}

@Dao
interface TodoItemDao {

    @Insert
    suspend fun insert(item: TodoItemEntity)

    @Update
    suspend fun update(item: TodoItemEntity)

    @Delete
    suspend fun delete(item: TodoItemEntity)

    @Query("SELECT * from TodoItemEntity WHERE id = :id")
    fun getItem(id: Int): Flow<TodoItemEntity>

    @Query("SELECT * from TodoItemEntity ORDER BY id")
    fun getAllItems(): List<TodoItemEntity>

}




