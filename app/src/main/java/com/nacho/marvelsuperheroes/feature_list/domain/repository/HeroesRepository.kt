package com.nacho.marvelsuperheroes.feature_list.domain.repository

interface HeroesRepository {

    suspend fun getHeroes(): ResultResponse

    suspend fun getHeroById(id: Int): ResultResponse
}