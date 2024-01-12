package com.arboleda.sistecreditoskilltest.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.arboleda.sistecreditoskilltest.R
import com.arboleda.sistecreditoskilltest.domain.models.GameDetail
import com.arboleda.sistecreditoskilltest.domain.models.Screenshots
import com.arboleda.sistecreditoskilltest.presentation.states.GameDetailState
import com.arboleda.sistecreditoskilltest.presentation.viewmodels.GameDetailViewModel

@Composable
fun GameDetailScreen(
    navController: NavHostController,
    id: Int,
    gameDetailViewModel: GameDetailViewModel,
) {
    gameDetailViewModel.getGameDetail(id)
    val showErrorDialog: Boolean by gameDetailViewModel.showErrorDialog.collectAsState()
    val gameDetailState by gameDetailViewModel.gameDetailState.collectAsState()

    when (gameDetailState) {
        GameDetailState.onLoading -> {
            ShowLoader()
        }

        is GameDetailState.onError -> {
            ShowErrorDialog(
                showErrorDialog = showErrorDialog,
                understood = { gameDetailViewModel.closeErrorDialog() },
                error = (gameDetailState as GameDetailState.onError).error,
            )
        }

        is GameDetailState.onSuccess -> {
            ShowGameDetail(
                navController,
                (gameDetailState as GameDetailState.onSuccess).gameDetail,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowGameDetail(navController: NavHostController, gameDetail: GameDetail) {
    Scaffold(
        bottomBar = {
            MenuOptions(navController, modifier = Modifier)
        },
        floatingActionButton = {
            ButtonAddFavorites(
                modifier = Modifier,
                gameDetail = gameDetail,
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { contentPadding ->
        LazyColumn(modifier = Modifier.padding(contentPadding).fillMaxWidth()) {
            item {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (
                        backButton,
                        containerGameDetail,
                    ) = createRefs()

                    ContainerGameDetail(
                        gameDetail,
                        Modifier.constrainAs(containerGameDetail) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    )

                    BackButton(
                        navController,
                        Modifier
                            .padding(top = dimensionResource(id = R.dimen.dimen_16dp))
                            .constrainAs(backButton) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                            },
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonAddFavorites(modifier: Modifier, gameDetail: GameDetail) {
    val context = LocalContext.current
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            // TODO: implement add favorite game with room
        },
        containerColor = Color.White,
        contentColor = Color.Red,
    ) {
        Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
    }
}

@Composable
fun ContainerGameDetail(gameDetail: GameDetail, modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        GameDetailImageComponent(gameDetail)
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dimen_16dp)))
        GameDetailTitleComponent(modifier, gameDetail)
        GameDetailDataComponent(modifier, gameDetail)
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dimen_16dp)))
        GameDetailScreenshotsComponent(modifier, gameDetail.screenshots)
    }
}

@Composable
fun GameDetailScreenshotsComponent(modifier: Modifier, screenshots: List<Screenshots>) {
    if (screenshots.isNotEmpty()) {
        val openSans = FontFamily(Font(R.font.open_sans))
        Text(
            text = stringResource(R.string.game_detail_screen_screenshots_title),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = openSans,
            modifier = modifier.padding(start = dimensionResource(id = R.dimen.dimen_16dp)),
        )
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dimen_8dp)))
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.dimen_8dp),
                    end = dimensionResource(id = R.dimen.dimen_8dp),
                    top = dimensionResource(id = R.dimen.dimen_8dp),
                    bottom = dimensionResource(id = R.dimen.dimen_16dp),
                ),
        ) {
            items(screenshots, key = { it.id }) { screenshot ->
                ScreenshotItem(screenshot)
            }
        }
    }
}

@Composable
fun ScreenshotItem(screenshot: Screenshots) {
    AsyncImage(
        model = screenshot.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dimen_200dp))
            .clip(
                RoundedCornerShape(size = dimensionResource(id = R.dimen.dimen_4dp)),
            ),
    )
}

@Composable
fun GameDetailImageComponent(gameDetail: GameDetail) {
    AsyncImage(
        model = gameDetail.thumbnail,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dimen_240dp))
            .clip(
                RoundedCornerShape(
                    bottomStart = dimensionResource(id = R.dimen.dimen_16dp),
                    bottomEnd = dimensionResource(id = R.dimen.dimen_16dp),
                ),
            ),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun GameDetailTitleComponent(modifier: Modifier, gameDetail: GameDetail) {
    val openSans = FontFamily(Font(R.font.open_sans))
    Text(
        modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.dimen_8dp)),
        text = gameDetail.title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = openSans,
    )
}

@Composable
fun GameDetailDataComponent(modifier: Modifier, gameDetail: GameDetail) {
    val openSans = FontFamily(Font(R.font.open_sans))
    Column(modifier = modifier.padding(top = dimensionResource(id = R.dimen.dimen_12dp))) {
        GameDetailDeveloperComponent(modifier, gameDetail, openSans)
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dimen_8dp)))
        GameDetailUrlComponent(modifier, gameDetail, openSans)
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dimen_16dp)))
        Divider(modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.dimen_8dp)))
        Spacer(modifier = modifier.size(dimensionResource(id = R.dimen.dimen_16dp)))
        GameDetailDescriptionComponent(modifier, gameDetail, openSans)
    }
}

@Composable
fun GameDetailDeveloperComponent(modifier: Modifier, gameDetail: GameDetail, openSans: FontFamily) {
    Row(modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.dimen_8dp))) {
        Text(
            text = stringResource(R.string.game_detail_screen_developer_title),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = openSans,
        )
        Text(
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dimen_8dp)),
            text = gameDetail.developer,
            fontSize = 14.sp,
            fontFamily = openSans,
        )
    }
}

@Composable
fun GameDetailUrlComponent(modifier: Modifier, gameDetail: GameDetail, openSans: FontFamily) {
    Row(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.dimen_8dp),
            ),
    ) {
        Text(
            text = stringResource(R.string.game_detail_screen_url_title),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = openSans,
        )
        Text(
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dimen_8dp)),
            text = gameDetail.gameUrl,
            maxLines = 1,
            fontSize = 12.sp,
            fontFamily = openSans,
        )
    }
}

@Composable
fun GameDetailDescriptionComponent(
    modifier: Modifier,
    gameDetail: GameDetail,
    openSans: FontFamily,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.dimen_12dp)),
        text = gameDetail.description,
        fontSize = 12.sp,
        fontFamily = openSans,
    )
}

@Composable
fun BackButton(navController: NavHostController, modifier: Modifier) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = null,
        modifier = modifier
            .padding(start = dimensionResource(id = R.dimen.dimen_16dp))
            .clickable { navController.popBackStack() },
    )
}
