package com.arboleda.sistecreditoskilltest.domain.usecases

import com.arboleda.sistecreditoskilltest.domain.repositories.network.GameDetailRepository
import javax.inject.Inject

class GetGameDetailUC @Inject constructor(private val gameDetailRepository: GameDetailRepository) {
    suspend operator fun invoke(id: Int) = gameDetailRepository.getGameDetail(id)
}
