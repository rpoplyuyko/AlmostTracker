package com.example.almosttracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
public abstract class ItemRoomDatabase : RoomDatabase() {
    abstract fun locationDAO() : LocationDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ItemRoomDatabase? = null

        fun getDatabase(context: Context): ItemRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemRoomDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}