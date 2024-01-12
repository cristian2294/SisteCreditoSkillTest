package com.arboleda.sistecreditoskilltest.data.models

import com.google.gson.annotations.SerializedName
import com.arboleda.sistecreditoskilltest.domain.models.Game as GameDomain

data class Game(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("short_description")
    val shortDescription: String,
    @SerializedName("game_url")
    val gameUrl: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("developer")
    val developer: String,
    @SerializedName("release_date")
    val releaseDate: String,
)

fun Game.toDomain(): GameDomain =
    GameDomain(
        id = id,
        title = title,
        thumbnail = thumbnail,
        shortDescription = shortDescription,
        gameUrl = gameUrl,
        genre = genre,
        platform = platform,
        publisher = publisher,
        developer = developer,
        releaseDate = releaseDate,
    )
