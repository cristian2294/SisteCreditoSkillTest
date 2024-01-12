package com.arboleda.sistecreditoskilltest.data.endpoints

import com.arboleda.sistecreditoskilltest.data.models.Game
import com.arboleda.sistecreditoskilltest.utils.Constants.Companion.GAMES
import retrofit2.Response
import retrofit2.http.GET

interface GameApi {
    @GET(GAMES)
    suspend fun getGames(): Response<List<Game>>
}
