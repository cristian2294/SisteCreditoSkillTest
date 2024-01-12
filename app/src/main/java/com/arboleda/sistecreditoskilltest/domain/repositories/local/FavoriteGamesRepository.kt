package com.arboleda.sistecreditoskilltest.domain.repositories.local

import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import kotlinx.coroutines.flow.Flow

interface FavoriteGamesRepository {

    suspend fun addFavoriteGame(favoriteGame: FavoriteGame)

    suspend fun removeFavoriteGame(favoriteGame: FavoriteGame)

    fun getFavoriteGames(): Flow<List<FavoriteGame>>
}
