package com.example.masterdetailexample.domain.repositories

import com.example.masterdetailexample.data.local.dao.FavoritesDao
import com.example.masterdetailexample.data.local.entity.FavoriteEntity
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val dao: FavoritesDao
){
    suspend fun deleteFavorite(name: String){
        dao.deleteFavorite(name)
    }

    suspend fun saveFavorite(name: String){
        dao.insertFavorite(favoriteEntity = FavoriteEntity(0, name))
    }
}