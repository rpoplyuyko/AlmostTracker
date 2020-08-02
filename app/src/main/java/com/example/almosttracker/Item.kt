package com.example.almosttracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
class Item (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "item_address") val item_address: String,
    @ColumnInfo(name = "item_latitude") var item_lat: String,
    @ColumnInfo(name = "item_longtitude") val item_lon: String,
    @ColumnInfo(name = "item_date") val item_date: String
)