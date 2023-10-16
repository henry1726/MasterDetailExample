package com.example.masterdetailexample.domain.models

data class CharacterPokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<DataPaging>
)