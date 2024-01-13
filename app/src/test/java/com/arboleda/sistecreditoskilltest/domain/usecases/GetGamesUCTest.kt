package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.models.Game
import com.arboleda.sistecreditoskilltest.domain.repositories.network.GameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetGamesUCTest {

    private val gameRepository: GameRepository = mockk(relaxed = true)

    private lateinit var getGamesUC: GetGamesUC

    @Before
    fun setUp() {
        getGamesUC = GetGamesUC(gameRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(gameRepository)
    }

    @Test
    fun `getGamesUC should return a list of games`() = runBlocking {
        // Given
        val game1 = Game(
            id = 1,
            title = "Mi Juego",
            thumbnail = "https://example.com/imagen.jpg",
            shortDescription = "Descripción corta del juego",
            gameUrl = "https://example.com/juego",
            genre = "Acción",
            platform = "PC",
            publisher = "Editora Ejemplo",
            developer = "Desarrollador Ejemplo",
            releaseDate = "2023-07-01",
        )
        val game2 = Game(
            id = 2,
            title = "Mi Juego2",
            thumbnail = "https://example.com/imagen.jpg",
            shortDescription = "Descripción corta del juego",
            gameUrl = "https://example.com/juego",
            genre = "Acción",
            platform = "PC",
            publisher = "Editora Ejemplo",
            developer = "Desarrollador Ejemplo",
            releaseDate = "2023-07-01",
        )
        val games = listOf(game1, game2)
        coEvery { gameRepository.getGames() } returns games

        // When
        val result = getGamesUC.invoke()

        // Then
        assertEquals(games, result)
        coVerify { gameRepository.getGames() }
    }
}
