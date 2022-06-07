package com.nacho.marvelsuperheroes.feature_list.presentation.hero_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.marvelsuperheroes.feature_list.domain.repository.ResultResponse
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.HeroesUseCases
import com.nacho.marvelsuperheroes.feature_list.presentation.util.HeroesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val heroesUseCases: HeroesUseCases
) : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<HeroesUiState>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun getHeroById(id: Int) {
        viewModelScope.launch {
            _sharedFlow.emit(HeroesUiState.Loading)
            heroesUseCases.getHeroByIdUseCase(id).also {
                if (it.heroes.isNullOrEmpty()) {
                    _sharedFlow.emit(HeroesUiState.Error(it.message))
                } else {
                    _sharedFlow.emit(HeroesUiState.Success(it.heroes))
                }
            }
        }
    }

}