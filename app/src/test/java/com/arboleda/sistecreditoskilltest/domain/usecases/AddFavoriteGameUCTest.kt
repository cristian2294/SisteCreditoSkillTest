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

class AddFavoriteGameUCTest {

    private val favoriteGamesRepository: FavoriteGamesRepository = mockk(relaxed = true)

    private lateinit var addFavoriteGameUC: AddFavoriteGameUC

    @Before
    fun setUp() {
        addFavoriteGameUC = AddFavoriteGameUC(favoriteGamesRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(favoriteGamesRepository)
    }

    @Test
    fun `should add a game to favorite games`() = runBlocking {
        // Given
        val favoriteGame = FavoriteGame(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        coEvery { favoriteGamesRepository.addFavoriteGame(favoriteGame) } returns Unit

        // When
        addFavoriteGameUC.invoke(favoriteGame)

        // Then
        coVerify { favoriteGamesRepository.addFavoriteGame(any()) }
    }
}
