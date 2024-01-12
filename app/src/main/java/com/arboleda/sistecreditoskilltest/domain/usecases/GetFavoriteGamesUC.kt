package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.domain.repositories.local.FavoriteGamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteGamesUC @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository,
) {
    operator fun invoke(): Flow<List<FavoriteGame>> =
        favoriteGamesRepository.getFavoriteGames()
}
