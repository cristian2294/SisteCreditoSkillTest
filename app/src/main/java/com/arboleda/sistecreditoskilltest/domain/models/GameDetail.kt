package com.arboleda.sistecreditoskilltest.domain.models

import com.arboleda.sistecreditoskilltest.data.models.MinimumSystemRequirements
import com.arboleda.sistecreditoskilltest.data.models.Screenshots

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
)

data class MinimumSystemRequirements(
    val graphics: String,
    val memory: String,
    val os: String,
    val processor: String,
    val storage: String,
)

data class Screenshots(
    val id: Int,
    val image: String,
)
