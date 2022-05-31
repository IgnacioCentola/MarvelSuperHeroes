package com.nacho.marvelsuperheroes.feature_list.data.remote

import com.nacho.marvelsuperheroes.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val API_KEY = BuildConfig.API_KEY

interface HeroService {
    @GET("/v1/public/characters")
    suspend fun getHeroes(@Query("key") key: String = API_KEY): HeroResponse

    @GET("/v1/public/characters/{characterId}")
    suspend fun getHeroById(
        @Path("characterId") id: Int,
        @Query("key") key: String = API_KEY
    ): HeroResponse
}