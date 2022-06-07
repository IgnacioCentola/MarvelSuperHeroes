package com.nacho.marvelsuperheroes.feature_list.presentation.util

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero

sealed class HeroesUiState{
    data class Success(val heroes : List<Hero>?) : HeroesUiState()
    object Loading : HeroesUiState()
    data class Error(val errorMsg : String) : HeroesUiState()
}
