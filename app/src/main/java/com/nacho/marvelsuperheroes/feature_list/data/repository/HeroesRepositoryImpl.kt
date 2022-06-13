package com.nacho.marvelsuperheroes.feature_list.data.repository

import com.google.gson.Gson
import com.nacho.marvelsuperheroes.feature_list.data.remote.HeroApiError
import com.nacho.marvelsuperheroes.feature_list.data.remote.HeroService
import com.nacho.marvelsuperheroes.feature_list.domain.repository.HeroesRepository
import com.nacho.marvelsuperheroes.feature_list.domain.model.ResultResponse
import java.io.IOException
import javax.inject.Inject

class HeroesRepositoryImpl @Inject constructor(private val heroService: HeroService) :
    HeroesRepository {
    override suspend fun getHeroes() = getHeroResult()

    override suspend fun getHeroById(id: Int) = getHeroResult(id)

    private suspend fun getHeroResult(id: Int? = null): ResultResponse {

        val response = try {
            if (id == null) heroService.getHeroes()
            else heroService.getHeroById(id)
        } catch (ex: IOException) {
            return ResultResponse(
                heroes = null,
                message = ex.message.toString()
            )
        }

        return if (response.isSuccessful) {
            ResultResponse(response.body()?.data?.results)
        } else {
            val gson = Gson()
            val heroError = gson.fromJson(response.errorBody()?.charStream(), HeroApiError::class.java)
            ResultResponse(
                heroes = null,
                message = heroError.message
            )
        }
    }
}