package com.nacho.marvelsuperheroes.feature_list.presentation.heroes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nacho.marvelsuperheroes.feature_list.domain.repository.ResultResponse
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.HeroesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val heroesUseCases: HeroesUseCases
) : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<ResultResponse>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        getHeroes()
    }

    fun getHeroes() {
        viewModelScope.launch {
            heroesUseCases.getHeroesUseCase().also {
                _sharedFlow.emit(it)
            }
        }
    }

}