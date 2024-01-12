package com.arboleda.sistecreditoskilltest.domain.repositories.network

import com.arboleda.sistecreditoskilltest.domain.models.Game

interface GameRepository {
    suspend fun getGames(): List<Game>
}
