package com.arboleda.sistecreditoskilltest.data.repositories.network

import com.arboleda.sistecreditoskilltest.data.endpoints.GameApi
import com.arboleda.sistecreditoskilltest.data.models.Game
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GameRepositoryImplTest {

    private val gameApi: GameApi = mockk(relaxed = true)

    private lateinit var gameRepositoryImpl: GameRepositoryImpl

    @Before
    fun setUp() {
        gameRepositoryImpl = GameRepositoryImpl(gameApi)
    }

    @After
    fun tearDown() {
        confirmVerified(gameApi)
    }

    @Test
    fun `get games should return list of games`() = runBlocking {
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
        coEvery { gameApi.getGames() } returns Response.success(games)

        // When
        gameRepositoryImpl.getGames()

        // Then
        coVerify { gameApi.getGames() }
    }
}
