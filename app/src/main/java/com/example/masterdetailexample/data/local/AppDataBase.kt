package com.example.masterdetailexample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.masterdetailexample.data.local.dao.FavoritesDao
import com.example.masterdetailexample.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun favoritesDao(): FavoritesDao
}