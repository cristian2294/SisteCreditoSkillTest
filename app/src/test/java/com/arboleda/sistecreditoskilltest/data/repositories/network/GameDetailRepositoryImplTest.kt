package com.arboleda.sistecreditoskilltest.data.repositories.network

import com.arboleda.sistecreditoskilltest.data.endpoints.GameDetailApi
import com.arboleda.sistecreditoskilltest.data.models.GameDetail
import com.arboleda.sistecreditoskilltest.data.models.MinimumSystemRequirements
import com.arboleda.sistecreditoskilltest.data.models.Screenshots
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GameDetailRepositoryImplTest {

    private val gameDetailApi: GameDetailApi = mockk(relaxed = true)

    private lateinit var gameDetailRepositoryImpl: GameDetailRepositoryImpl

    @Before
    fun setUp() {
        gameDetailRepositoryImpl = GameDetailRepositoryImpl(gameDetailApi)
    }

    @After
    fun tearDown() {
        confirmVerified(gameDetailApi)
    }

    @Test
    fun `get game detail should return a game detail`() = runBlocking {
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

        coEvery { gameDetailApi.getGameDetail(id) } returns Response.success(gameDetail)

        // When
        gameDetailRepositoryImpl.getGameDetail(id)

        // Then
        coVerify { gameDetailApi.getGameDetail(any()) }
    }
}
