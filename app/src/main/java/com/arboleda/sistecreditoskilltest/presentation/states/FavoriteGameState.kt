package com.arboleda.sistecreditoskilltest.presentation.states

import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame

sealed class FavoriteGameState {

    object onLoading : FavoriteGameState()
    data class onSuccess(val favoriteGames: List<FavoriteGame>) : FavoriteGameState()
    data class onError(val error: Throwable) : FavoriteGameState()
}
