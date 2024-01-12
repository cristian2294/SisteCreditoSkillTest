package com.arboleda.sistecreditoskilltest.data.repositories.local

import com.arboleda.sistecreditoskilltest.data.db.dao.FavoriteGameDAO
import com.arboleda.sistecreditoskilltest.data.db.entities.FavoriteGameEntity
import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.domain.repositories.local.FavoriteGamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteGamesRepositoryImpl @Inject constructor(
    private val favoriteGameDAO: FavoriteGameDAO,
) : FavoriteGamesRepository {

    val games: Flow<List<FavoriteGame>> =
        favoriteGameDAO.getFavoriteGames().map { gamesEntity ->
            gamesEntity.map { gameEntity ->
                gameEntity.toDomain()
            }
        }

    override suspend fun addFavoriteGame(favoriteGame: FavoriteGame) {
        favoriteGameDAO.addFavoriteGame(favoriteGame.toEntity())
    }

    override suspend fun removeFavoriteGame(favoriteGame: FavoriteGame) {
        favoriteGameDAO.removeFavoriteGame(favoriteGame.toEntity())
    }

    override fun getFavoriteGames(): Flow<List<FavoriteGame>> = games
    fun FavoriteGame.toEntity(): FavoriteGameEntity {
        return FavoriteGameEntity(
            id = this.id,
            title = this.title,
            thumbnail = this.thumbnail,
            developer = this.developer,
        )
    }
}
