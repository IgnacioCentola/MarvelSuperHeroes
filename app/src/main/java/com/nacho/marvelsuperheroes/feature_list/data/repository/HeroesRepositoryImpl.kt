package com.nacho.marvelsuperheroes.feature_list.data.repository

import com.nacho.marvelsuperheroes.feature_list.data.remote.HeroService
import com.nacho.marvelsuperheroes.feature_list.domain.repository.HeroesRepository
import com.nacho.marvelsuperheroes.feature_list.domain.repository.ResultResponse
import javax.inject.Inject

class HeroesRepositoryImpl @Inject constructor(private val heroService: HeroService) :
    HeroesRepository {
    override suspend fun getHeroes() = getHeroResult()

    override suspend fun getHeroById(id: Int) = getHeroResult(id)

    private suspend fun getHeroResult(id: Int? = null): ResultResponse {
        var exception = ""
        val result = try {
            if (id == null) heroService.getHeroes()
            else heroService.getHeroById(id)
        } catch (ex: Exception) {
            exception = ex.message.toString()
            null
        }

        return if (result != null && result.code in 200..299) {
            ResultResponse(result.data.results)
        } else {
            ResultResponse(
                heroes = null,
                message = exception
            )
        }
    }

}