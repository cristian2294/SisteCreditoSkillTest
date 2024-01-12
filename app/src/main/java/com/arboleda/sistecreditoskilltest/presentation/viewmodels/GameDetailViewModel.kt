package com.arboleda.sistecreditoskilltest.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arboleda.sistecreditoskilltest.domain.usecases.GetGameDetailUC
import com.arboleda.sistecreditoskilltest.presentation.states.GameDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameDetailUC: GetGameDetailUC,
) : ViewModel() {

    private val _gameDetailState = MutableStateFlow<GameDetailState>(GameDetailState.onLoading)
    val gameDetailState: StateFlow<GameDetailState> get() = _gameDetailState

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    fun getGameDetail(id: Int) {
        viewModelScope.launch {
            try {
                _gameDetailState.value = GameDetailState.onSuccess(getGameDetailUC.invoke(id))
            } catch (error: Exception) {
                _gameDetailState.value = GameDetailState.onError(error)
                _showErrorDialog.value = true
            }
        }
    }

    fun closeErrorDialog() {
        _showErrorDialog.value = false
    }
}
