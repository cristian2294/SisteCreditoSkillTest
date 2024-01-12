package com.arboleda.sistecreditoskilltest.di

import android.content.Context
import androidx.room.Room
import com.arboleda.sistecreditoskilltest.data.db.FavoriteGamesDataBase
import com.arboleda.sistecreditoskilltest.utils.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoriteGamesDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = FavoriteGamesDataBase::class.java,
            name = DATABASE_NAME,
        ).build()
    }
}
