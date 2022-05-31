package com.nacho.marvelheroes.feature_list.data.repository

import com.nacho.marvelsuperheroes.feature_list.data.remote.HeroService
import com.nacho.marvelsuperheroes.feature_list.domain.repository.HeroesRepository
import com.nacho.marvelsuperheroes.feature_list.domain.repository.ResultResponse
import javax.inject.Inject

class HeroesRepositoryImpl @Inject constructor(private val heroService: HeroService) :
    HeroesRepository {
    override suspend fun getHeroes() = getHeroResult()

    override suspend fun getHeroById(id: Int) = getHeroResult(id)

    private suspend fun getHeroResult(id: Int? = null): ResultResponse {
        val result =
            if (id == null) heroService.getHeroes()
            else heroService.getHeroById(id)

        return if (result.code in 200..299) {
            ResultResponse(result.data.results)
        } else {
            ResultResponse(
                hero = null,
                message = result.status
            )
        }
    }

}