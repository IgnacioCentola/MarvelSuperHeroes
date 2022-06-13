package com.nacho.marvelsuperheroes.feature_list.domain.repository

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.*
import com.nacho.marvelsuperheroes.feature_list.domain.model.ResultResponse
import java.io.IOException

class FakeHeroesRepository : HeroesRepository {

    private val heroes = arrayListOf(
        Hero(
            id = 1,
            name = "name",
            description = "dsc",
            modified = "now",
            thumbnail = Thumbnail("", ""),
            resourceURI = "uri",
            etag = "tag",
            comics = Comics(0, "", emptyList(), 1),
            stories = Stories(0, "", emptyList(), 1),
            series = Series(0, "", emptyList(), 1),
            events = Events(0, "", emptyList(), 1),
            urls = emptyList()
        )
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
        ResultResponse(null, IOException().message.toString())
    } else {
        ResultResponse(heroes)
    }
}