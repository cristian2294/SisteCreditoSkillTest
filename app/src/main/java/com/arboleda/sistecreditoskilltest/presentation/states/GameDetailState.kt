package com.arboleda.sistecreditoskilltest.presentation.states

import com.arboleda.sistecreditoskilltest.domain.models.GameDetail

sealed class GameDetailState {

    object onLoading : GameDetailState()
    data class onSuccess(val gameDetail: GameDetail) : GameDetailState()
    data class onError(val error: Throwable) : GameDetailState()
}
