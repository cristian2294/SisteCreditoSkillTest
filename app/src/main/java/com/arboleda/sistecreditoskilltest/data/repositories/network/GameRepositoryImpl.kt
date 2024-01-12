package com.arboleda.sistecreditoskilltest.data.repositories.network

import com.arboleda.sistecreditoskilltest.data.endpoints.GameApi
import com.arboleda.sistecreditoskilltest.data.models.toDomain
import com.arboleda.sistecreditoskilltest.domain.models.Game
import com.arboleda.sistecreditoskilltest.domain.repositories.network.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val gameApi: GameApi) : GameRepository {
    /**
     We need to change the  dispatcher for not block the main thread
     also we need map the object to the domain layer for return the list
     */
    override suspend fun getGames(): List<Game> {
        return withContext(Dispatchers.IO) {
            val games = gameApi.getGames()
            games.body()?.map { it.toDomain() } ?: emptyList()
        }
    }
}
