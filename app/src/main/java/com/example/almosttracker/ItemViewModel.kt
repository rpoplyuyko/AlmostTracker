package com.example.almosttracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository
    val allWords: LiveData<List<Item>>

    init {
        val locationsDAO = ItemRoomDatabase.getDatabase(application).locationDAO()
        repository = ItemRepository(locationsDAO)
        allWords = repository.allWords
    }
    fun insert(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }
}