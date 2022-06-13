package com.nacho.marvelsuperheroes.feature_list.domain.repository

import com.nacho.marvelsuperheroes.feature_list.domain.model.ResultResponse

interface HeroesRepository {

    suspend fun getHeroes(): ResultResponse

    suspend fun getHeroById(id: Int): ResultResponse
}