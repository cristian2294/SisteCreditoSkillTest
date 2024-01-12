package com.arboleda.sistecreditoskilltest.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arboleda.sistecreditoskilltest.data.db.entities.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGameDAO {
    @Query("SELECT * FROM favoriteGame ORDER BY id ASC")
    fun getFavoriteGames(): Flow<List<FavoriteGameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteGame(game: FavoriteGameEntity)

    @Delete
    suspend fun removeFavoriteGame(game: FavoriteGameEntity)
}
