package com.nacho.marvelsuperheroes.feature_list.presentation.hero_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.HeroesUseCases
import com.nacho.marvelsuperheroes.feature_list.presentation.util.HeroesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val heroesUseCases: HeroesUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow<HeroesUiState>(HeroesUiState.Loading)
    val uiState: StateFlow<HeroesUiState> = _uiState

    fun getHeroById(id: Int) {
        viewModelScope.launch {
            heroesUseCases.getHeroByIdUseCase(id).also {
                if (it.heroes.isNullOrEmpty()) {
                    _uiState.value = HeroesUiState.Error(it.message)
                } else {
                    _uiState.value = HeroesUiState.Success(it.heroes)
                }
            }
        }
    }

}