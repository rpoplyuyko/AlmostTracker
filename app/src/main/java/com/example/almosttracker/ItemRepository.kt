package com.example.almosttracker

import androidx.lifecycle.LiveData

class ItemRepository(private val locationDao: LocationDAO) {
    val allWords: LiveData<List<Item>> = locationDao.getAlphabetizedWords()

    suspend fun insert(item: Item) {
        locationDao.insert(item)
    }
}