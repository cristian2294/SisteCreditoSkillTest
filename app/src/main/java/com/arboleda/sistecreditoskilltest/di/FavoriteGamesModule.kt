package com.arboleda.sistecreditoskilltest.di

import com.arboleda.sistecreditoskilltest.data.db.FavoriteGamesDataBase
import com.arboleda.sistecreditoskilltest.data.db.dao.FavoriteGameDAO
import com.arboleda.sistecreditoskilltest.data.repositories.local.FavoriteGamesRepositoryImpl
import com.arboleda.sistecreditoskilltest.domain.repositories.local.FavoriteGamesRepository
import com.arboleda.sistecreditoskilltest.domain.usecases.AddFavoriteGameUC
import com.arboleda.sistecreditoskilltest.domain.usecases.GetFavoriteGamesUC
import com.arboleda.sistecreditoskilltest.domain.usecases.RemoveFavoriteGameUC
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.FavoriteGamesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object FavoriteGamesModule {

    @Provides
    @ViewModelScoped
    fun provideFavoriteGamesDAO(
        favoriteGamesDataBase: FavoriteGamesDataBase,
    ): FavoriteGameDAO = favoriteGamesDataBase.favoriteGamesDAO()

    @Provides
    @ViewModelScoped
    fun provideFavoriteGamesRepository(
        favoriteGameDAO: FavoriteGameDAO,
    ): FavoriteGamesRepository = FavoriteGamesRepositoryImpl(favoriteGameDAO = favoriteGameDAO)

    @Provides
    @ViewModelScoped
    fun provideAddFavoriteGameUC(
        favoriteGamesRepository: FavoriteGamesRepository,
    ): AddFavoriteGameUC = AddFavoriteGameUC(favoriteGamesRepository = favoriteGamesRepository)

    @Provides
    @ViewModelScoped
    fun provideRemoveFavoriteGameUC(
        favoriteGamesRepository: FavoriteGamesRepository,
    ): RemoveFavoriteGameUC =
        RemoveFavoriteGameUC(favoriteGamesRepository = favoriteGamesRepository)

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteGamesUC(
        favoriteGamesRepository: FavoriteGamesRepository,
    ): GetFavoriteGamesUC =
        GetFavoriteGamesUC(favoriteGamesRepository = favoriteGamesRepository)

    @Provides
    @ViewModelScoped
    fun provideFavoriteGamesViewModel(
        addFavoriteGameUC: AddFavoriteGameUC,
        removeFavoriteGameUC: RemoveFavoriteGameUC,
        getFavoriteGamesUC: GetFavoriteGamesUC,
    ): FavoriteGamesViewModel = FavoriteGamesViewModel(
        addFavoriteGameUC = addFavoriteGameUC,
        removeFavoriteGameUC = removeFavoriteGameUC,
        getFavoriteGamesUC = getFavoriteGamesUC,
    )
}
