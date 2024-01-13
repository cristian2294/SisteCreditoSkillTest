package com.arboleda.sistecreditoskilltest.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.domain.usecases.AddFavoriteGameUC
import com.arboleda.sistecreditoskilltest.domain.usecases.GetFavoriteGamesUC
import com.arboleda.sistecreditoskilltest.domain.usecases.RemoveFavoriteGameUC
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteGamesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val addFavoriteGameUC: AddFavoriteGameUC = mockk(relaxed = true)
    private val removeFavoriteGameUC: RemoveFavoriteGameUC = mockk(relaxed = true)
    private val getFavoriteGamesUC: GetFavoriteGamesUC = mockk(relaxed = true)
    private val _showErrorDialog: MutableStateFlow<Boolean> = mockk(relaxed = true)

    private lateinit var favoriteGamesViewModel: FavoriteGamesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        favoriteGamesViewModel = FavoriteGamesViewModel(
            addFavoriteGameUC,
            removeFavoriteGameUC,
            getFavoriteGamesUC,
            _showErrorDialog,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(
            addFavoriteGameUC,
            removeFavoriteGameUC,
            getFavoriteGamesUC,
            _showErrorDialog,
        )
    }

    @Test
    fun `add favorite game`() = runTest {
        // Given
        val favoriteGame = FavoriteGame(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        coEvery { addFavoriteGameUC.invoke(favoriteGame) } returns Unit

        // When
        favoriteGamesViewModel.addFavoriteGame(favoriteGame)

        // Then
        coVerify {
            getFavoriteGamesUC.invoke()
            addFavoriteGameUC.invoke(any())
        }
    }

    @Test
    fun `remove favorite game`() = runTest {
        // Given
        val favoriteGame = FavoriteGame(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        coEvery { removeFavoriteGameUC.invoke(favoriteGame) } returns Unit

        // When
        favoriteGamesViewModel.removeFavoriteGame(favoriteGame)

        // Then
        coVerify {
            getFavoriteGamesUC.invoke()
            removeFavoriteGameUC.invoke(any())
        }
    }

    @Test
    fun `get favorite game success`() = runTest {
        // Given
        val favoriteGame1 = FavoriteGame(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        val favoriteGame2 = FavoriteGame(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            developer = "Desarrollador del juego",
        )

        val favoriteGames = listOf(favoriteGame1, favoriteGame2)

        coEvery { getFavoriteGamesUC.invoke() } returns flow { favoriteGames }

        // When
        favoriteGamesViewModel.favoriteGameState.value

        // Then
        coVerify { getFavoriteGamesUC.invoke() }
    }
}
