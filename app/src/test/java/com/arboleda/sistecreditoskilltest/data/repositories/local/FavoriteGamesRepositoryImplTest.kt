package com.arboleda.sistecreditoskilltest.data.repositories.local

import com.arboleda.sistecreditoskilltest.data.db.dao.FavoriteGameDAO
import com.arboleda.sistecreditoskilltest.data.db.entities.FavoriteGameEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavoriteGamesRepositoryImplTest {

    private val favoriteGameDAO: FavoriteGameDAO = mockk(relaxed = true)

    private lateinit var favoriteGameRepositoryImpl: FavoriteGamesRepositoryImpl

    @Before
    fun setUp() {
        favoriteGameRepositoryImpl = FavoriteGamesRepositoryImpl(favoriteGameDAO)
    }

    @After
    fun tearDown() {
        confirmVerified(favoriteGameDAO)
    }

    @Test fun `get favorite games`() = runBlocking {
        // Given
        val favoriteGameEntity = FavoriteGameEntity(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        val favoriteGames = listOf(favoriteGameEntity)

        coEvery { favoriteGameDAO.getFavoriteGames() } returns flow { favoriteGames }

        // When
        favoriteGameRepositoryImpl.getFavoriteGames()

        // Then
        coVerify { favoriteGameDAO.getFavoriteGames() }
    }

    @Test
    fun `add favorite game`() = runBlocking {
        // Given
        val favoriteGameEntity = FavoriteGameEntity(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        coEvery { favoriteGameDAO.addFavoriteGame(favoriteGameEntity) } returns Unit

        // When
        favoriteGameRepositoryImpl.addFavoriteGame(favoriteGameEntity.toDomain())

        // Then
        coVerifyOrder {
            favoriteGameDAO.getFavoriteGames()
            favoriteGameDAO.addFavoriteGame(favoriteGameEntity)
        }
    }

    @Test
    fun `remove favorite game`() = runBlocking {
        // Given
        val favoriteGameEntity = FavoriteGameEntity(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        coEvery { favoriteGameDAO.removeFavoriteGame(favoriteGameEntity) } returns Unit

        // When
        favoriteGameRepositoryImpl.removeFavoriteGame(favoriteGameEntity.toDomain())

        // Then
        coVerifyOrder {
            favoriteGameDAO.getFavoriteGames()
            favoriteGameDAO.removeFavoriteGame(favoriteGameEntity)
        }
    }
}
