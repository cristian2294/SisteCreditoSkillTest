package com.arboleda.sistecreditoskilltest.domain.repositories.network

import com.arboleda.sistecreditoskilltest.domain.models.GameDetail

interface GameDetailRepository {
    suspend fun getGameDetail(id: Int): GameDetail
}
