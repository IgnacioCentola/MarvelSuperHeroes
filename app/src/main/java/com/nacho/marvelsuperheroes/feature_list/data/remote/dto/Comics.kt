package com.nacho.marvelsuperheroes.feature_list.data.remote.dto

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Comic>,
    val returned: Int
)