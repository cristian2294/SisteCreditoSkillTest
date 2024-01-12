package com.arboleda.sistecreditoskilltest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arboleda.sistecreditoskilltest.data.db.dao.FavoriteGameDAO
import com.arboleda.sistecreditoskilltest.data.db.entities.FavoriteGameEntity

@Database(entities = [FavoriteGameEntity::class], version = 1)
abstract class FavoriteGamesDataBase : RoomDatabase() {
    abstract fun favoriteGamesDAO(): FavoriteGameDAO
}
