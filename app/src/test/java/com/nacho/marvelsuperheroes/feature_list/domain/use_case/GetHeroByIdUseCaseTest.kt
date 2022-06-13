package com.nacho.marvelsuperheroes.feature_list.domain.use_case

import com.nacho.marvelsuperheroes.feature_list.data.remote.dto.*
import com.nacho.marvelsuperheroes.feature_list.domain.repository.FakeHeroesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import java.io.IOException

class GetHeroByIdUseCaseTest {

    private lateinit var fakeHeroesRepository: FakeHeroesRepository
    private lateinit var getHeroByIdUseCase: GetHeroByIdUseCase
    private lateinit var hero: Hero

    @Before
    fun setUp() {
        fakeHeroesRepository = FakeHeroesRepository()
        getHeroByIdUseCase = GetHeroByIdUseCase(fakeHeroesRepository)
        hero = Hero(
            id = 1,
            name = "name",
            description = "dsc",
            modified = "now",
            thumbnail = Thumbnail("", ""),
            resourceURI = "uri",
            etag = "tag",
            comics = Comics(0, "", emptyList(), 1),
            stories = Stories(0, "", emptyList(), 1),
            series = Series(0, "", emptyList(), 1),
            events = Events(0, "", emptyList(), 1),
            urls = emptyList()
        )
    }

    @Test
    fun getHeroById_success_returnHeroListWithSingleHero() {
        runBlocking {

            fakeHeroesRepository.addHero(hero)

            val result = getHeroByIdUseCase(hero.id)

            assertEquals(result.heroes?.size, 1)
            assertEquals(fakeHeroesRepository.getHeroes().heroes, result.heroes)

        }
    }

    @Test
    fun getHeroById_networkError_returnNullListAndErrorMessage() {
        runBlocking {
            fakeHeroesRepository.setShouldReturnNetworkError(true)
            val result = getHeroByIdUseCase(hero.id)

            assertEquals(null, result.heroes)
            assertEquals(IOException().message.toString(), result.message)
        }
    }
}