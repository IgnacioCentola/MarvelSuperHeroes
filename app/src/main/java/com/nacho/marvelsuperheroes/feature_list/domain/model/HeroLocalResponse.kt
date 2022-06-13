package com.nacho.marvelsuperheroes.feature_list.domain.model

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero

data class HeroLocalResponse(
    val heroes: List<Hero>?,
    val message: String = ""
)