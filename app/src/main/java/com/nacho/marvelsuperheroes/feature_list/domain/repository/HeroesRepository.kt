package com.nacho.marvelsuperheroes.feature_list.domain.repository

import com.nacho.marvelsuperheroes.feature_list.domain.model.HeroLocalResponse

interface HeroesRepository {

    suspend fun getHeroes(): HeroLocalResponse

    suspend fun getHeroById(id: Int): HeroLocalResponse
}