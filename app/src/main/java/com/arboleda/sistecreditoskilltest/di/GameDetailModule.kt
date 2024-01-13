package com.arboleda.sistecreditoskilltest.di

import com.arboleda.sistecreditoskilltest.data.endpoints.GameDetailApi
import com.arboleda.sistecreditoskilltest.data.repositories.network.GameDetailRepositoryImpl
import com.arboleda.sistecreditoskilltest.domain.repositories.network.GameDetailRepository
import com.arboleda.sistecreditoskilltest.domain.usecases.GetGameDetailUC
import com.arboleda.sistecreditoskilltest.presentation.states.GameDetailState
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.GameDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object GameDetailModule {

    @Provides
    @ViewModelScoped
    fun provideGameDetailApi(retrofit: Retrofit): GameDetailApi {
        return retrofit.create(GameDetailApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideGameDetailRepository(gameDetailApi: GameDetailApi): GameDetailRepository {
        return GameDetailRepositoryImpl(gameDetailApi)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDetailGameUC(gameDetailRepository: GameDetailRepository): GetGameDetailUC {
        return GetGameDetailUC(gameDetailRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGameDetailViewModel(
        getDetailGameUC: GetGameDetailUC,
    ): GameDetailViewModel {
        return GameDetailViewModel(
            getGameDetailUC = getDetailGameUC,
            _gameDetailState = MutableStateFlow<GameDetailState>(GameDetailState.onLoading),
            _showErrorDialog = MutableStateFlow<Boolean>(false),
        )
    }
}
