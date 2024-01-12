package com.arboleda.sistecreditoskilltest.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame

@Entity(tableName = "favoriteGame")
data class FavoriteGameEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "developer")
    val developer: String,
) {
    fun toDomain(): FavoriteGame = FavoriteGame(id, title, thumbnail, developer)
}
