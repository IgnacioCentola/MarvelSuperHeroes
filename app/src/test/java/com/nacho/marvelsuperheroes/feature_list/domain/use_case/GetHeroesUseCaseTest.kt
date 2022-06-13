package com.nacho.marvelsuperheroes.feature_list.domain.use_case

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.Hero
import com.nacho.marvelsuperheroes.feature_list.domain.repository.FakeHeroesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetHeroesUseCaseTest {

    private lateinit var getHeroesUseCase: GetHeroesUseCase
    private lateinit var fakeHeroesRepository: FakeHeroesRepository

    @Before
    fun setUp() {
        fakeHeroesRepository = FakeHeroesRepository()
        getHeroesUseCase = GetHeroesUseCase(fakeHeroesRepository)
    }

    @Test
    fun getHeroes_success_returnHeroList(){
        runBlocking {
            val result = getHeroesUseCase()
            assertNotNull(result.heroes)
            assertEquals(arrayListOf<Hero>(), result.heroes)
        }
    }

    @Test
    fun getHeroes_networkError_returnNullListAndErrorMessage(){
        runBlocking {
            fakeHeroesRepository.setShouldReturnNetworkError(true)
            val result = getHeroesUseCase()
            assertNull(result.heroes)
            assertEquals(IOException().message.toString(), result.message)
        }
    }
}