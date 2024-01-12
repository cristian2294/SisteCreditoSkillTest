package com.arboleda.sistecreditoskilltest.presentation.states

import com.arboleda.sistecreditoskilltest.domain.models.Game

sealed class GameState {

    object onLoading : GameState()

    data class onError(val error: Throwable) : GameState()
    data class onSuccess(val games: List<Game>) : GameState()
}
