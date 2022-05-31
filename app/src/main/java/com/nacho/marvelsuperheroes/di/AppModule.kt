package com.nacho.marvelsuperheroes.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nacho.marvelsuperheroes.R
import com.nacho.marvelsuperheroes.feature_list.data.remote.HeroService
import com.nacho.marvelsuperheroes.feature_list.data.repository.HeroesRepositoryImpl
import com.nacho.marvelsuperheroes.feature_list.domain.repository.HeroesRepository
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.GetHeroByIdUseCase
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.GetHeroesUseCase
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.HeroesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(context.getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideHeroService(retrofit: Retrofit): HeroService =
        retrofit.create(HeroService::class.java)

    @Provides
    fun provideHeroRepository(heroService: HeroService): HeroesRepository =
        HeroesRepositoryImpl(heroService)

    @Provides
    fun provideHeroUseCases(repository: HeroesRepository) =
        HeroesUseCases(
            getHeroesUseCase = GetHeroesUseCase(repository),
            getHeroByIdUseCase = GetHeroByIdUseCase(repository)
        )
}