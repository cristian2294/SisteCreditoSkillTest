package com.arboleda.sistecreditoskilltest.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arboleda.sistecreditoskilltest.data.models.GameDetail
import com.arboleda.sistecreditoskilltest.data.models.MinimumSystemRequirements
import com.arboleda.sistecreditoskilltest.data.models.Screenshots
import com.arboleda.sistecreditoskilltest.domain.usecases.GetGameDetailUC
import com.arboleda.sistecreditoskilltest.presentation.states.GameDetailState
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
class GameDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getGameDetailUC: GetGameDetailUC = mockk(relaxed = true)
    private val _gameDetailState: MutableStateFlow<GameDetailState> = mockk(relaxed = true)
    private val _showErrorDialog: MutableStateFlow<Boolean> = mockk(relaxed = true)

    private lateinit var gameDetailViewModel: GameDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        gameDetailViewModel = GameDetailViewModel(
            getGameDetailUC = getGameDetailUC,
            _gameDetailState = _gameDetailState,
            _showErrorDialog = _showErrorDialog,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(getGameDetailUC, _gameDetailState, _showErrorDialog)
    }

    @Test
    fun `getGameDetail is Success`() = runTest {
        // Given
        val id = 1
        val gameDetail = GameDetail(
            id = 1,
            title = "Nombre del juego",
            thumbnail = "ruta/imagen.jpg",
            gameUrl = "https://ejemplo.com/juego",
            genre = "Acción",
            developer = "Desarrollador del juego",
            releaseDate = "2022-01-01",
            description = "Descripción del juego",
            minimumSystemRequirements = MinimumSystemRequirements(
                graphics = "NVIDIA GeForce GTX 1060",
                memory = "8 GB",
                os = "Windows 10",
                processor = "Intel Core i5",
                storage = "50 GB",
            ),
            screenshots = listOf(
                Screenshots(
                    id = 1,
                    image = "ruta/captura1.jpg",
                ),
                Screenshots(
                    id = 2,
                    image = "ruta/captura2.jpg",
                ),
            ),
        )

        coEvery { getGameDetailUC.invoke(id) } returns gameDetail.toDomain()
        coEvery { _gameDetailState.value } returns GameDetailState.onSuccess(gameDetail.toDomain())
        coEvery { _showErrorDialog.value } returns false

        // When
        gameDetailViewModel.getGameDetail(id)

        // Then
        coVerifyOrder {
            getGameDetailUC.invoke(any())
            _gameDetailState.value = GameDetailState.onSuccess(gameDetail.toDomain())
        }
    }
}
