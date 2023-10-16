package com.example.masterdetailexample.domain.repositories

import com.example.masterdetailexample.data.remote.api.MasterDetailApi
import javax.inject.Inject

class MasterDetailRepository @Inject constructor(
    private val api: MasterDetailApi
) {
   suspend fun getAllPokemon() = api.getAllPokemon()

}