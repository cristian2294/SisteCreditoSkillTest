package com.arboleda.sistecreditoskilltest.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.arboleda.sistecreditoskilltest.R
import com.arboleda.sistecreditoskilltest.domain.models.Game
import com.arboleda.sistecreditoskilltest.presentation.states.GameState
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.GamesViewModel
import com.arboleda.sistecreditoskilltest.utils.Routes

@Composable
fun GamesScreen(navController: NavHostController, gamesViewModel: GamesViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (menuOptions, games) = createRefs()
        ListGames(
            navController,
            gamesViewModel,
            Modifier.constrainAs(games) {
                top.linkTo(parent.top, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )

        MenuOptions(
            navController,
            Modifier.constrainAs(menuOptions) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )
    }
}

@Composable
fun ListGames(
    navController: NavHostController,
    gamesViewModel: GamesViewModel,
    modifier: Modifier,
) {
    val showErrorDialog: Boolean by gamesViewModel.showErrorDialog.collectAsState()
    val gameState by gamesViewModel.gameState.collectAsState()
    when (gameState) {
        is GameState.onSuccess -> ShowGames(
            modifier = modifier,
            navController = navController,
            games = (gameState as GameState.onSuccess).games,
        )

        GameState.onLoading -> ShowLoader()

        is GameState.onError -> ShowErrorDialog(
            showErrorDialog = showErrorDialog,
            understood = { gamesViewModel.closeErrorDialog() },
            error = (gameState as GameState.onError).error,
        )
    }
}

@Composable
fun ShowGames(
    modifier: Modifier,
    navController: NavHostController,
    games: List<Game>,
) {
    LazyColumn(
        modifier = modifier.padding(
            start = dimensionResource(id = R.dimen.dimen_16dp),
            end = dimensionResource(id = R.dimen.dimen_16dp),
            top = dimensionResource(id = R.dimen.dimen_16dp),
        ),
    ) {
        items(games, key = { it.id }) { game ->
            ItemGame(game = game, navController = navController)
        }
    }
}

@Composable
fun ItemGame(game: Game, navController: NavHostController) {
    val openSans = FontFamily(Font(R.font.open_sans))
    val iconPlatForm = getIconForPlatform(game.platform)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.dimen_16dp))
            .clickable {
                // TODO: navigate to game detail
            },
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.dimen_4dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        AsyncImage(
            model = game.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = game.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = openSans,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.dimen_8dp),
                top = dimensionResource(id = R.dimen.dimen_12dp),
            ),
            color = Color.Black,
        )

        Text(
            text = game.shortDescription,
            fontSize = 12.sp,
            fontFamily = openSans,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.dimen_8dp),
                end = dimensionResource(id = R.dimen.dimen_8dp),
                top = dimensionResource(id = R.dimen.dimen_8dp),
            ),
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.dimen_8dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Icon(
                imageVector = iconPlatForm,
                contentDescription = stringResource(
                    id = R.string.home_screen_content_description_developer,
                ),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.dimen_40dp))
                    .padding(
                        end = dimensionResource(id = R.dimen.dimen_4dp),
                        bottom = dimensionResource(id = R.dimen.dimen_8dp),
                    ),
            )
            Text(
                text = game.genre,
                fontSize = 14.sp,
                fontFamily = openSans,
                color = Color.Black,
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.dimen_4dp),
                        end = dimensionResource(id = R.dimen.dimen_8dp),
                        bottom = dimensionResource(id = R.dimen.dimen_8dp),
                    ),
            )
        }
    }
}

fun getIconForPlatform(platform: String): ImageVector {
    return if (platform == PC_WINDOWS) {
        Icons.Default.Computer
    } else {
        Icons.Default.DesktopWindows
    }
}

@Composable
fun ShowLoader() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowErrorDialog(
    showErrorDialog: Boolean,
    understood: () -> Unit,
    error: Throwable,
) {
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { understood() },
            title = {
                Text(
                    text = stringResource(
                        id = R.string.home_screen_error_dialog_title,
                    ),
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.home_screen_error_dialog_description,
                        error.toString(),
                    ),
                )
            },
            confirmButton = {
                TextButton(onClick = { understood() }) {
                    Text(
                        text = stringResource(
                            id = R.string.home_screen_error_dialog_button_understood,
                        ),
                    )
                }
            },
        )
    }
}

@Composable
fun MenuOptions(navController: NavHostController, modifier: Modifier) {
    var index by rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current

    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBarItem(
            selected = index == 0,
            onClick = {
                index = 0
                navController.navigate(Routes.homeScreen.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(
                        id = R.string.home_screen_menu_option_home,
                    ),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.home_screen_menu_option_home))
            },
        )
        NavigationBarItem(
            selected = index == 1,
            onClick = {
                index = 1
                // TODO: navigate to favorites screen
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(
                        id = R.string.home_screen_menu_option_favorites,
                    ),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.home_screen_menu_option_favorites))
            },
        )
        NavigationBarItem(
            selected = index == 2,
            onClick = {
                index = 2
                Toast.makeText(
                    context,
                    R.string.categories_screen_soon,
                    Toast.LENGTH_SHORT,
                ).show()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = stringResource(
                        id = R.string.home_screen_menu_option_categories,
                    ),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.home_screen_menu_option_categories))
            },
        )
    }
}

private const val PC_WINDOWS = "PC (Windows)"
