package com.example.masterdetailexample.domain.models

data class DataPaging(
    val id: Int,
    val name: String,
    val url: String,
    val isFavorite: Boolean = false
)
