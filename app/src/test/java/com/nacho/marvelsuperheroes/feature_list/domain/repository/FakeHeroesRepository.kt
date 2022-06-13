package com.nacho.marvelsuperheroes.feature_list.domain.repository

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.*
import com.nacho.marvelsuperheroes.feature_list.domain.model.HeroLocalResponse
import com.nacho.marvelsuperheroes.feature_list.getFakeHero
import java.io.IOException

class FakeHeroesRepository : HeroesRepository {

    private val heroes = arrayListOf(
        getFakeHero()
    )

    private var shouldReturnNetworkError = false

    fun addHero(hero: Hero) {
        heroes.add(hero)
    }

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getHeroes() = heroes()

    override suspend fun getHeroById(id: Int) = heroes()

    private fun heroes() = if (shouldReturnNetworkError) {
        HeroLocalResponse(null, IOException().message.toString())
    } else {
        HeroLocalResponse(heroes)
    }
}