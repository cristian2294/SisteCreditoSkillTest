package com.arboleda.sistecreditoskilltest.utils

import com.arboleda.sistecreditoskilltest.utils.Constants.Companion.GAME_DETAIL_SCREEN
import com.arboleda.sistecreditoskilltest.utils.Constants.Companion.HOME_SCREEN

sealed class Routes(val route: String) {
    object homeScreen : Routes(HOME_SCREEN)
    object gameDetailScreen : Routes(GAME_DETAIL_SCREEN) {
        fun createRoute(id: Int) = "game_detail_screen/$id"
    }
}
