package com.arboleda.sistecreditoskilltest.domain.models

data class GameDetail(
    val id: Int = 0,
    val title: String = "",
    val thumbnail: String = "",
    val gameUrl: String = "",
    val genre: String = "",
    val developer: String = "",
    val releaseDate: String = "",
    val description: String = "",
    val minimumSystemRequirements: MinimumSystemRequirements? = null,
    val screenshots: List<Screenshots> = arrayListOf(),
) {
    fun toFavoriteGame(): FavoriteGame {
        return FavoriteGame(
            id = id,
            title = title,
            thumbnail = thumbnail,
            developer = developer,
        )
    }
}

data class MinimumSystemRequirements(
    val graphics: String? = "",
    val memory: String? = "",
    val os: String? = "",
    val processor: String? = "",
    val storage: String? = "",
)

data class Screenshots(
    val id: Int,
    val image: String,
)
