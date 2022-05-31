package com.nacho.marvelsuperheroes.feature_list.data.remote

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero

data class QueryData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results : List<Hero>
)