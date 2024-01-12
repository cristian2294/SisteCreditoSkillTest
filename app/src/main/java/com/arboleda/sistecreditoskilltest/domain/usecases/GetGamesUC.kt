package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.repositories.GameRepository
import javax.inject.Inject

class GetGamesUC @Inject constructor(private val gameRepository: GameRepository) {
    suspend operator fun invoke() = gameRepository.getGames()
}
