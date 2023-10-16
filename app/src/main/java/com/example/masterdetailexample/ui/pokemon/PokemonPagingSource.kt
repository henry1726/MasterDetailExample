package com.example.masterdetailexample.ui.pokemon

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.masterdetailexample.domain.models.DataPaging
import com.example.masterdetailexample.domain.repositories.MasterDetailRepository
import java.util.HashMap

class PokemonPagingSource(
    private val repository: MasterDetailRepository
) : PagingSource<Int, DataPaging>() {
    override fun getRefreshKey(state: PagingState<Int, DataPaging>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataPaging> {
        return try {
            val currentPage = params.key ?: 1
            val parameters = HashMap<String, String>()
            parameters["page"] = currentPage.toString()
            val response  = repository.getAllPokemon()
            val responseData = mutableListOf<DataPaging>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}