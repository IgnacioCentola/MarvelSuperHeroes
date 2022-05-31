package com.nacho.marvelsuperheroes.feature_list.domain.use_case

data class HeroesUseCases(
    val getHeroesUseCase: GetHeroesUseCase,
    val getHeroByIdUseCase: GetHeroByIdUseCase
)