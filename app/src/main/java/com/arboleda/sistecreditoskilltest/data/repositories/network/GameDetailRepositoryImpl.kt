package com.arboleda.sistecreditoskilltest.data.repositories.network

import com.arboleda.sistecreditoskilltest.data.endpoints.GameDetailApi
import com.arboleda.sistecreditoskilltest.domain.models.GameDetail
import com.arboleda.sistecreditoskilltest.domain.repositories.network.GameDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameDetailRepositoryImpl @Inject constructor(
    private val gameDetailApi: GameDetailApi,
) : GameDetailRepository {

    /**
     *  We need map the game detail to  one object of the domain layer
     *  also if the game doesn't have value, for default I defined empty attributes
     *  in the data class of the data layer for the nullability
     */
    override suspend fun getGameDetail(id: Int): GameDetail {
        return withContext(Dispatchers.IO) {
            val gameDetail = gameDetailApi.getGameDetail(id)
            gameDetail.body()?.toDomain() ?: GameDetail()
        }
    }
}
