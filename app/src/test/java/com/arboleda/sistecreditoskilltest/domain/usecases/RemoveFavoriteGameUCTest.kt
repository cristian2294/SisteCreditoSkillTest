package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.domain.repositories.local.FavoriteGamesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class RemoveFavoriteGameUCTest {

    private val favoriteGamesRepository: FavoriteGamesRepository = mockk(relaxed = true)

    private lateinit var removeFavoriteGameUC: RemoveFavoriteGameUC

    @Before
    fun setUp() {
        removeFavoriteGameUC = RemoveFavoriteGameUC(favoriteGamesRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(favoriteGamesRepository)
    }

    @Test
    fun `should remove a game to favorite games`() = runBlocking {
        // Given
        val favoriteGame = FavoriteGame(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        coEvery { favoriteGamesRepository.removeFavoriteGame(favoriteGame) } returns Unit

        // When
        removeFavoriteGameUC.invoke(favoriteGame)

        // Then
        coVerify { favoriteGamesRepository.removeFavoriteGame(any()) }
    }
}
