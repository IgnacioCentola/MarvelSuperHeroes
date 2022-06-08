package com.nacho.marvelsuperheroes.feature_list.data.remote

import com.nacho.marvelsuperheroes.BuildConfig
import com.nacho.marvelsuperheroes.feature_list.data.util.md5
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val API_KEY = BuildConfig.API_KEY
private const val TIME_STAMP = 1
private val HASH = "$TIME_STAMP${BuildConfig.PRIVATE_API_KEY}${BuildConfig.API_KEY}".md5()


interface HeroService {
    @GET("/v1/public/characters")
    suspend fun getHeroes(
        @Query("apikey") key: String = API_KEY,
        @Query("ts") timeStamp: Int = TIME_STAMP,
        @Query("hash") hash: String = HASH
    ): Response<HeroResponse>

    @GET("/v1/public/characters/{characterId}")
    suspend fun getHeroById(
        @Path("characterId") id: Int,
        @Query("apikey") key: String = API_KEY,
        @Query("ts") timeStamp: Int = TIME_STAMP,
        @Query("hash") hash: String = HASH
    ): Response<HeroResponse>
}