package com.nacho.marvelsuperheroes.feature_list.domain.use_case

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.*
import com.nacho.marvelsuperheroes.feature_list.domain.repository.FakeHeroesRepository
import com.nacho.marvelsuperheroes.feature_list.getFakeHero
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class GetHeroByIdUseCaseTest {

    private lateinit var fakeHeroesRepository: FakeHeroesRepository
    private lateinit var getHeroByIdUseCase: GetHeroByIdUseCase
    private lateinit var hero: Hero

    @Before
    fun setUp() {
        fakeHeroesRepository = FakeHeroesRepository()
        getHeroByIdUseCase = GetHeroByIdUseCase(fakeHeroesRepository)
        hero = getFakeHero()
    }

    @Test
    fun getHeroById_success_returnHeroListWithSingleHero() {
        runBlocking {

            fakeHeroesRepository.addHero(hero)

            val result = getHeroByIdUseCase(hero.id)

            assertNotNull(result.heroes)
            assertEquals(result.heroes?.size, 1)
            assertEquals(fakeHeroesRepository.getHeroes().heroes, result.heroes)

        }
    }

    @Test
    fun getHeroById_networkError_returnNullListAndErrorMessage() {
        runBlocking {
            fakeHeroesRepository.setShouldReturnNetworkError(true)
            val result = getHeroByIdUseCase(hero.id)

            assertNull(result.heroes)
            assertEquals(IOException().message.toString(), result.message)
        }
    }
}