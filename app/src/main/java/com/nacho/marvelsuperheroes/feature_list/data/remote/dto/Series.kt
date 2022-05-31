package com.nacho.marvelsuperheroes.feature_list.data.remote.dto

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Serie

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Serie>,
    val returned: Int
)