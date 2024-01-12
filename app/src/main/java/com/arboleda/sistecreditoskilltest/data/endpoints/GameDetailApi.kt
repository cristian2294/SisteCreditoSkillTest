package com.arboleda.sistecreditoskilltest.data.endpoints

import com.arboleda.sistecreditoskilltest.data.models.GameDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GameDetailApi {
    @GET("game?")
    suspend fun getGameDetail(@Query("id") id: Int): Response<GameDetail>
}
