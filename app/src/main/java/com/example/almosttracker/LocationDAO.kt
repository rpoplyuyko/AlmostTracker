package com.example.almosttracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDAO {
    @Query("SELECT * FROM item_table ORDER BY id DESC")
    fun getAlphabetizedWords(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()

    @Query("DELETE FROM item_table WHERE id=1")
    suspend fun deleteFirst()

    @Query("SELECT COUNT(*) FROM item_table")
    fun getCount() : Int
}