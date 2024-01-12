package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.domain.repositories.local.FavoriteGamesRepository
import javax.inject.Inject

class AddFavoriteGameUC @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository,
) {
    suspend operator fun invoke(favoriteGame: FavoriteGame) =
        favoriteGamesRepository.addFavoriteGame(favoriteGame)
}
