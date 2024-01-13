package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.models.GameDetail
import com.arboleda.sistecreditoskilltest.domain.models.MinimumSystemRequirements
import com.arboleda.sistecreditoskilltest.domain.models.Screenshots
import com.arboleda.sistecreditoskilltest.domain.repositories.network.GameDetailRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetGameDetailUCTest {

    private val gameDetailRepository: GameDetailRepository = mockk(relaxed = true)

    private lateinit var getGameDetailUC: GetGameDetailUC

    @Before
    fun setUp() {
        getGameDetailUC = GetGameDetailUC(gameDetailRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(gameDetailRepository)
    }

    @Test
    fun `getGameDetailUC should return a detail game`() = runBlocking {
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

        coEvery { gameDetailRepository.getGameDetail(id) } returns gameDetail

        // When
        val result = getGameDetailUC.invoke(id)

        // Then
        assertEquals(gameDetail, result)
        coVerify { gameDetailRepository.getGameDetail(any()) }
    }
}
