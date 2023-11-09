package com.example.todokt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoItemEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun getTodoItemDao(): TodoItemDao

    companion object {
        @Volatile
        private var Instance: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TodoDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}
