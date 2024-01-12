package com.arboleda.sistecreditoskilltest.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.arboleda.sistecreditoskilltest.R
import com.arboleda.sistecreditoskilltest.domain.models.FavoriteGame
import com.arboleda.sistecreditoskilltest.presentation.states.FavoriteGameState
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.FavoriteGamesViewModel

@Composable
fun FavoriteGamesScreen(
    navController: NavHostController,
    favoriteGamesViewModel: FavoriteGamesViewModel,
) {
    val showErrorDialog: Boolean by favoriteGamesViewModel.showErrorDialog.collectAsState()

    val favoriteGameState by favoriteGamesViewModel.favoriteGameState.collectAsState()

    when (favoriteGameState) {
        is FavoriteGameState.onError -> {
            ShowErrorDialog(
                showErrorDialog = showErrorDialog,
                understood = { favoriteGamesViewModel.closeErrorDialog() },
                error = (favoriteGameState as FavoriteGameState.onError).error,
            )
        }

        FavoriteGameState.onLoading -> ShowLoader()

        is FavoriteGameState.onSuccess -> {
            ShowFavoriteGames(
                favoriteGamesViewModel,
                navController,
                (favoriteGameState as FavoriteGameState.onSuccess).favoriteGames,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowFavoriteGames(
    favoriteGamesViewModel: FavoriteGamesViewModel,
    navController: NavHostController,
    favoriteGames: List<FavoriteGame>,
) {
    Scaffold(
        bottomBar = {
            MenuOptions(navController = navController, modifier = Modifier)
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
        ) {
            item {
                BackButton(
                    navController,
                    Modifier.padding(top = dimensionResource(id = R.dimen.dimen_16dp)),
                )
            }
            items(favoriteGames, key = { it.id }) { favoriteGame ->
                FavoriteGameItem(
                    favoriteGamesViewModel,
                    favoriteGame,
                )
            }
        }
    }
}

@Composable
fun FavoriteGameItem(
    favoriteGamesViewModel: FavoriteGamesViewModel,
    favoriteGame: FavoriteGame,
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val containerFavoriteGame = createRef()
        ContainerFavoriteGame(
            favoriteGamesViewModel,
            favoriteGame,
            Modifier
                .constrainAs(containerFavoriteGame) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
        )
    }
}

@Composable
fun ContainerFavoriteGame(
    favoriteGamesViewModel: FavoriteGamesViewModel,
    favoriteGame: FavoriteGame,
    modifier: Modifier,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(id = R.dimen.dimen_4dp),
                start = dimensionResource(id = R.dimen.dimen_16dp),
                end = dimensionResource(id = R.dimen.dimen_16dp),
                bottom = dimensionResource(id = R.dimen.dimen_16dp),
            ),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.dimen_4dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        ConstraintLayout(modifier = modifier.fillMaxWidth()) {
            val (
                favoriteGameImageComponent,
                favoriteGameDataComponent,
                removeFavoriteGameComponent,
            ) = createRefs()

            FavoriteGameImageComponent(
                favoriteGame.thumbnail,
                modifier = Modifier.constrainAs(favoriteGameImageComponent) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            )

            FavoriteGameDataComponent(
                favoriteGame,
                modifier = Modifier.constrainAs(favoriteGameDataComponent) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(favoriteGameImageComponent.end, margin = 4.dp)
                },
            )

            RemoveFavoriteGameComponent(
                favoriteGame,
                favoriteGamesViewModel,
                modifier = Modifier.constrainAs(removeFavoriteGameComponent) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            )
        }
    }
}

@Composable
fun FavoriteGameImageComponent(
    image: String,
    modifier: Modifier,
) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.dimen_16dp),
                top = dimensionResource(id = R.dimen.dimen_16dp),
                bottom = dimensionResource(id = R.dimen.dimen_16dp),
            )
            .size(dimensionResource(id = R.dimen.dimen_120dp)),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun FavoriteGameDataComponent(
    favoriteGame: FavoriteGame,
    modifier: Modifier,
) {
    val openSans = FontFamily(Font(R.font.open_sans))
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = favoriteGame.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = openSans,
            color = Color.Black,
            maxLines = 1,
        )

        Text(
            text = favoriteGame.developer,
            fontSize = 14.sp,
            fontFamily = openSans,
            maxLines = 1,
            modifier = modifier.padding(
                top = dimensionResource(id = R.dimen.dimen_4dp),
                bottom = dimensionResource(id = R.dimen.dimen_4dp),
            ),
        )
    }
}

@Composable
fun RemoveFavoriteGameComponent(
    favoriteGame: FavoriteGame,
    favoriteGamesViewModel: FavoriteGamesViewModel,
    modifier: Modifier,
) {
    val context = LocalContext.current
    Icon(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.dimen_40dp))
            .padding(
                end = dimensionResource(id = R.dimen.dimen_16dp),
                top = dimensionResource(id = R.dimen.dimen_16dp),
            )
            .clickable {
                favoriteGamesViewModel.removeFavoriteGame(favoriteGame)
                Toast.makeText(
                    context,
                    R.string.favorite_games_button_remove_favorite,
                    Toast.LENGTH_SHORT,
                ).show()
            },
        imageVector = Icons.Default.Favorite,
        contentDescription = null,
        tint = Color.Red,
    )
}
