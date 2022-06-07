package com.nacho.marvelsuperheroes.feature_list.presentation.heroes

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
class HeroesViewModel @Inject constructor(
    private val heroesUseCases: HeroesUseCases
) : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<HeroesUiState>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun getHeroes() {
        viewModelScope.launch {
            _sharedFlow.emit(HeroesUiState.Loading)
            heroesUseCases.getHeroesUseCase().also {
                if (it.heroes.isNullOrEmpty()) {
                    _sharedFlow.emit(HeroesUiState.Error(it.message))
                } else {
                    _sharedFlow.emit(HeroesUiState.Success(it.heroes))
                }
            }
        }
    }

}