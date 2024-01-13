package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.domain.repositories.local.FavoriteGamesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetFavoriteGamesUCTest {

    private val favoriteGamesRepository: FavoriteGamesRepository = mockk(relaxed = true)

    private lateinit var getFavoriteGameUC: GetFavoriteGamesUC

    @Before
    fun setUp() {
        getFavoriteGameUC = GetFavoriteGamesUC(favoriteGamesRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(favoriteGamesRepository)
    }

    @Test
    fun `should get favorite games`() = runBlocking {
        // Given
        val favoriteGame = FavoriteGame(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        val favoriteGames = listOf(favoriteGame)

        coEvery { favoriteGamesRepository.getFavoriteGames() } returns flow { favoriteGames }

        // When
        getFavoriteGameUC.invoke()

        // Then
        coVerify { favoriteGamesRepository.getFavoriteGames() }
    }
}
