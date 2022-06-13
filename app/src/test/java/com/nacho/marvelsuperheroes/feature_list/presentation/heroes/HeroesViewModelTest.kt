package com.nacho.marvelsuperheroes.feature_list.presentation.heroes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nacho.marvelsuperheroes.feature_list.domain.repository.FakeHeroesRepository
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.GetHeroByIdUseCase
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.GetHeroesUseCase
import com.nacho.marvelsuperheroes.feature_list.domain.use_case.HeroesUseCases
import com.nacho.marvelsuperheroes.feature_list.presentation.MainCoroutineRule
import com.nacho.marvelsuperheroes.feature_list.presentation.util.HeroesUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class HeroesViewModelTest {

    private lateinit var fakeHeroesRepository: FakeHeroesRepository
    private lateinit var viewModel: HeroesViewModel

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        fakeHeroesRepository = FakeHeroesRepository()
        viewModel = HeroesViewModel(
            HeroesUseCases(
                GetHeroesUseCase(fakeHeroesRepository),
                GetHeroByIdUseCase(fakeHeroesRepository)
            )
        )
    }

    @Test
    fun getHeroes_success_returnHeroList() {
        runBlockingTest {
            viewModel.getHeroes()
            val result = viewModel.uiState.value
            assertTrue(result is HeroesUiState.Success)
            assertNotNull((result as HeroesUiState.Success).heroes)
            assertEquals(fakeHeroesRepository.getHeroes().heroes, result.heroes)
        }
    }

    @Test
    fun getHeroes_error_returnNullListAndErrorMessage() {
        runBlockingTest {
            fakeHeroesRepository.setShouldReturnNetworkError(true)
            viewModel.getHeroes()
            val result = viewModel.uiState.value
            assertTrue(result is HeroesUiState.Error)
            val condition =
                (result as HeroesUiState.Error).errorMsg == IOException().message.toString()
            assertTrue(condition)
        }
    }
}
