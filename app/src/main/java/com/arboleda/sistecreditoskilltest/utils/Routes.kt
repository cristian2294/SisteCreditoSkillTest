package com.arboleda.sistecreditoskilltest.utils

import com.arboleda.sistecreditoskilltest.utils.Constants.Companion.HOME_SCREEN

sealed class Routes(val route: String) {
    object homeScreen : Routes(HOME_SCREEN)
}
