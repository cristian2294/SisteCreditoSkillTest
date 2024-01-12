package com.arboleda.sistecreditoskilltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arboleda.sistecreditoskilltest.presentation.screens.GameDetailScreen
import com.arboleda.sistecreditoskilltest.presentation.screens.GamesScreen
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.GameDetailViewModel
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.GamesViewModel
import com.arboleda.sistecreditoskilltest.ui.theme.SisteCreditoSkillTestTheme
import com.arboleda.sistecreditoskilltest.utils.Constants
import com.arboleda.sistecreditoskilltest.utils.Constants.Companion.GAME_ID
import com.arboleda.sistecreditoskilltest.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gamesViewModel: GamesViewModel by viewModels()
        val gameDetailViewModel: GameDetailViewModel by viewModels()
        setContent {
            SisteCreditoSkillTestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Constants.HOME_SCREEN,
                ) {
                    composable(route = Routes.homeScreen.route) {
                        GamesScreen(
                            navController,
                            gamesViewModel,
                        )
                    }
                    composable(
                        route = Routes.gameDetailScreen.route,
                        arguments = listOf(navArgument(GAME_ID) { type = NavType.IntType }),
                    ) { input ->
                        GameDetailScreen()
                    }
                }
            }
        }
    }
}
