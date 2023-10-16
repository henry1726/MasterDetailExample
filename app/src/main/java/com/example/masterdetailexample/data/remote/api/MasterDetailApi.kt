package com.example.masterdetailexample.data.remote.api

import com.example.masterdetailexample.domain.models.CharacterPokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface MasterDetailApi {
    @GET("pokemon")
    suspend fun getAllPokemon() : Response<CharacterPokemonResponse>
}