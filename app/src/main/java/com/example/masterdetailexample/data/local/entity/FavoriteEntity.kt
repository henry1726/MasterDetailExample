package com.example.masterdetailexample.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)
