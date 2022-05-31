package com.nacho.marvelsuperheroes.feature_list.domain.use_case

import com.nacho.marvelsuperheroes.feature_list.domain.repository.HeroesRepository

class GetHeroesUseCase(private val repository: HeroesRepository) {

    suspend operator fun invoke() = repository.getHeroes()
}