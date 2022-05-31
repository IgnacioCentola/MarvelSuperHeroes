package com.nacho.marvelsuperheroes.feature_list.data.remote.dto

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<Story>,
    val returned: Int
)