package com.arboleda.sistecreditoskilltest.data.models

import com.google.gson.annotations.SerializedName
import com.arboleda.sistecreditoskilltest.domain.models.GameDetail as GameDetailDomain
import com.arboleda.sistecreditoskilltest.domain.models.MinimumSystemRequirements as MinimumSystemRequirementsDomain
import com.arboleda.sistecreditoskilltest.domain.models.Screenshots as ScreenshotsDomain

data class GameDetail(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("game_url")
    val gameUrl: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("developer")
    val developer: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("minimum_system_requirements")
    val minimumSystemRequirements: MinimumSystemRequirements? = null,
    @SerializedName("screenshots")
    val screenshots: List<Screenshots> = arrayListOf(),
) {
    fun toDomain(): GameDetailDomain {
        return GameDetailDomain(
            id = id,
            title = title,
            thumbnail = thumbnail,
            gameUrl = gameUrl,
            genre = genre,
            developer = developer,
            releaseDate = releaseDate,
            description = description,
            minimumSystemRequirements = minimumSystemRequirements?.toDomain(),
            screenshots = screenshots.map { it.toDomain() },
        )
    }
}

data class MinimumSystemRequirements(
    @SerializedName("graphics")
    val graphics: String,
    @SerializedName("memory")
    val memory: String,
    @SerializedName("os")
    val os: String,
    @SerializedName("processor")
    val processor: String,
    @SerializedName("storage")
    val storage: String,
) {
    fun toDomain(): MinimumSystemRequirementsDomain {
        return MinimumSystemRequirementsDomain(
            graphics = graphics,
            memory = memory,
            os = os,
            processor = processor,
            storage = storage,
        )
    }
}

data class Screenshots(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
) {
    fun toDomain(): ScreenshotsDomain {
        return ScreenshotsDomain(id = id, image = image)
    }
}
