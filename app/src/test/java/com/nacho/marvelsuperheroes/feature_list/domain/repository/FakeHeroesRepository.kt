package com.nacho.marvelsuperheroes.feature_list.domain.repository

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import com.nacho.marvelsuperheroes.feature_list.domain.model.ResultResponse
import java.io.IOException

class FakeHeroesRepository : HeroesRepository {

    private val heroes = arrayListOf<Hero>()

    private var shouldReturnNetworkError = false

    fun addHero(hero: Hero) {
        heroes.add(hero)
    }

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getHeroes() =
        if (shouldReturnNetworkError) {
            ResultResponse(null, IOException().message.toString())
        } else {
            ResultResponse(heroes)
        }

    override suspend fun getHeroById(id: Int) =
        if (shouldReturnNetworkError) {
            ResultResponse(null, IOException().message.toString())
        } else {
            ResultResponse(heroes)
        }
}