package com.nacho.marvelsuperheroes.feature_list.presentation.hero_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.*
import com.nacho.marvelsuperheroes.feature_list.domain.model.HeroLocalResponse
import com.nacho.marvelsuperheroes.feature_list.domain.repository.HeroesRepository
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.GetHeroByIdUseCase
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.GetHeroesUseCase
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.HeroesUseCases
import com.nacho.marvelsuperheroes.feature_list.getFakeHero
import com.nacho.marvelsuperheroes.feature_list.presentation.MainCoroutineRule
import com.nacho.marvelsuperheroes.feature_list.presentation.util.HeroesUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeroDetailViewModelTest {

    companion object {
        private const val ERROR_MESSAGE = "Error message"
    }

    @Mock
    private lateinit var heroesRepository: HeroesRepository

    @InjectMocks
    private lateinit var getHeroByIdUseCase: GetHeroByIdUseCase


    private lateinit var viewModel: HeroDetailViewModel


    private lateinit var hero: Hero

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        hero = getFakeHero()

        viewModel = HeroDetailViewModel(
            HeroesUseCases(
                GetHeroesUseCase(heroesRepository),
                getHeroByIdUseCase
            )
        )
    }

    @Test
    fun getHeroById_success_returnSuccessState() {
        runBlocking {

            val heroes = arrayListOf(hero)
            val response = HeroLocalResponse(heroes)

            `when`(heroesRepository.getHeroById(anyInt())).thenReturn(response)

            viewModel.getHeroById(hero.id)
            val result = viewModel.uiState.value

            assertTrue(result is HeroesUiState.Success)
            assertNotNull((result as HeroesUiState.Success).heroes)
            assertEquals(response.heroes, result.heroes)
        }
    }

    @Test
    fun getHeroById_error_returnErrorStateAndErrorMessage() {
        runBlocking {
            val response = HeroLocalResponse(null, ERROR_MESSAGE)
            `when`(heroesRepository.getHeroById(anyInt())).thenReturn(response)

            viewModel.getHeroById(hero.id)
            val result = viewModel.uiState.value

            assertTrue(result is HeroesUiState.Error)
            assertEquals(ERROR_MESSAGE, (result as HeroesUiState.Error).errorMsg)
        }
    }
}