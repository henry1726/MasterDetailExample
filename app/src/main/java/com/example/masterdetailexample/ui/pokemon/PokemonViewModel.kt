package com.example.masterdetailexample.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.masterdetailexample.domain.repositories.FavoritesRepository
import com.example.masterdetailexample.domain.repositories.MasterDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject
constructor(private val repository: MasterDetailRepository,
            private val favoritesRepository: FavoritesRepository): ViewModel() {
    val listData = Pager(PagingConfig(pageSize = 1)){
        PokemonPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    fun saveFavorite(name: String){
        viewModelScope.launch {
            favoritesRepository.saveFavorite(name)
        }
    }

    fun deleteFavorite(name: String) {
        viewModelScope.launch {
            favoritesRepository.deleteFavorite(name)
        }
    }

}