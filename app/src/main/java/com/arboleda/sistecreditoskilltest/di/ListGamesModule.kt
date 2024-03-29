package com.arboleda.sistecreditoskilltest.di

import com.arboleda.sistecreditoskilltest.data.endpoints.GameApi
import com.arboleda.sistecreditoskilltest.data.repositories.network.GameRepositoryImpl
import com.arboleda.sistecreditoskilltest.domain.repositories.network.GameRepository
import com.arboleda.sistecreditoskilltest.domain.usecases.GetGamesUC
import com.arboleda.sistecreditoskilltest.presentation.states.GameState
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.GamesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ListGamesModule {
    @Provides
    @ViewModelScoped
    fun provideGamesApi(retrofit: Retrofit): GameApi {
        return retrofit.create(GameApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideGameRepository(
        gameApi: GameApi,
    ): GameRepository = GameRepositoryImpl(gameApi = gameApi)

    @Provides
    @ViewModelScoped
    fun provideGetGameUC(
        gameRepository: GameRepository,
    ): GetGamesUC = GetGamesUC(gameRepository = gameRepository)

    @Provides
    @ViewModelScoped
    fun provideGamesViewModel(
        getGamesUC: GetGamesUC,
    ): GamesViewModel = GamesViewModel(
        getGamesUC = getGamesUC,
        _gameState = MutableStateFlow(GameState.onLoading),
        _showErrorDialog = MutableStateFlow(false),
    )
}
