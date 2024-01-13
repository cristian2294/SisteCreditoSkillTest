package com.arboleda.sistecreditoskilltest.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.domain.usecases.AddFavoriteGameUC
import com.arboleda.sistecreditoskilltest.domain.usecases.GetFavoriteGamesUC
import com.arboleda.sistecreditoskilltest.domain.usecases.RemoveFavoriteGameUC
import com.arboleda.sistecreditoskilltest.presentation.states.FavoriteGameState
import com.arboleda.sistecreditoskilltest.presentation.states.FavoriteGameState.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteGamesViewModel @Inject constructor(
    private val addFavoriteGameUC: AddFavoriteGameUC,
    private val removeFavoriteGameUC: RemoveFavoriteGameUC,
    getFavoriteGamesUC: GetFavoriteGamesUC,
    private val _showErrorDialog: MutableStateFlow<Boolean>,
) : ViewModel() {

    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    val favoriteGameState: StateFlow<FavoriteGameState> =
        getFavoriteGamesUC.invoke().map(::onSuccess)
            .catch {
                FavoriteGameState.onError(it)
                _showErrorDialog.value = true
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                FavoriteGameState.onLoading,
            )

    fun addFavoriteGame(favoriteGame: FavoriteGame) {
        viewModelScope.launch {
            addFavoriteGameUC.invoke(favoriteGame)
        }
    }

    fun removeFavoriteGame(favoriteGame: FavoriteGame) {
        viewModelScope.launch {
            removeFavoriteGameUC.invoke(favoriteGame)
        }
    }

    fun closeErrorDialog() {
        _showErrorDialog.value = false
    }
}
