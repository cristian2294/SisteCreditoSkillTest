package com.arboleda.sistecreditoskilltest.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arboleda.sistecreditoskilltest.domain.usecases.GetGamesUC
import com.arboleda.sistecreditoskilltest.presentation.states.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val getGamesUC: GetGamesUC) : ViewModel() {

    private val _gameState = MutableStateFlow<GameState>(GameState.onLoading)
    val gameState: StateFlow<GameState> get() = _gameState

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    init {
        getGames()
    }

    private fun getGames() {
        viewModelScope.launch {
            try {
                _gameState.value = GameState.onSuccess(getGamesUC())
            } catch (error: Exception) {
                _gameState.value = GameState.onError(error)
                _showErrorDialog.value = true
            }
        }
    }

    fun closeErrorDialog() {
        _showErrorDialog.value = false
    }
}
