package com.nacho.marvelsuperheroes.feature_list.domain.repository

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero

data class ResultResponse(
    val heroes: List<Hero>?,
    val message: String = ""
)