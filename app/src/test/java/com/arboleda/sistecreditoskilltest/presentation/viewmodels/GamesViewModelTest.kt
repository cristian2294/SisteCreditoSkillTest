package com.arboleda.sistecreditoskilltest.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arboleda.sistecreditoskilltest.domain.models.Game
import com.arboleda.sistecreditoskilltest.domain.usecases.GetGamesUC
import com.arboleda.sistecreditoskilltest.presentation.states.GameState
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GamesViewModelTest {

    private val getGamesUC: GetGamesUC = mockk(relaxed = true)
    private val _gamesState: MutableStateFlow<GameState> = mockk(relaxed = true)
    private val _showErrorDialog: MutableStateFlow<Boolean> = mockk(relaxed = true)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var gamesViewModel: GamesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        gamesViewModel = GamesViewModel(
            getGamesUC,
            _gamesState,
            _showErrorDialog,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(
            getGamesUC,
            _gamesState,
            _showErrorDialog,
        )
    }

    @Test
    fun `getGames is Success`() =
        runTest {
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
            coEvery { getGamesUC.invoke() } returns games
            coEvery { _gamesState.value } returns GameState.onSuccess(games)

            // Then
            coVerifyOrder {
                getGamesUC.invoke()
                _gamesState.value = GameState.onSuccess(emptyList())
            }
        }
}
