package com.example.coffeepoints.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.bumptech.glide.integration.compose.RequestState
import com.bumptech.glide.integration.compose.placeholder
import com.example.coffeepoints.R
import com.example.coffeepoints.getApplicationComponent
import com.example.coffeepoints.utils.ShowToast
import com.example.coffeepointsmenu.models.CoffeeMenuItem
import com.example.coffeepointsmenu.presentation.CoffeeMenuScreenState
import com.example.coffeepointsmenu.presentation.CoffeeMenuViewModel

@Composable
fun CoffeePointMenuScreen(
    paddingValues: PaddingValues,
    id: Int,
    onNextScreen: (Int) -> Unit,
    onTokenExpired: () -> Unit,
) {

    val component = getApplicationComponent()
        .getCoffeePointMenuComponentFactory().create()
    val viewModel: CoffeeMenuViewModel = viewModel(factory = component.getViewModelsFactory())
    val screenState = viewModel.screenState.collectAsState(CoffeeMenuScreenState.Loading)

    LaunchedEffect(Unit) {
        viewModel.getList(id)
    }

    CoffeePointMenuContent(
        id = id,
        paddingValues = paddingValues,
        modifier = Modifier,
        state = screenState,
        onNextScreen = onNextScreen,
        onItemIncrease = { viewModel.increaseAmount(it) },
        onItemDecrease = { viewModel.decreaseAmount(it) },
        onTokenExpired = onTokenExpired
    )
}

@Composable
fun CoffeePointMenuContent(
    id: Int,
    paddingValues: PaddingValues,
    modifier: Modifier,
    state: State<CoffeeMenuScreenState>,
    onNextScreen: (Int) -> Unit,
    onItemIncrease: (CoffeeMenuItem) -> Unit,
    onItemDecrease: (CoffeeMenuItem) -> Unit,
    onTokenExpired: () -> Unit,
) {
    val currentState = state.value

    if (currentState is CoffeeMenuScreenState.Content) {
        if (currentState.error is CoffeeMenuScreenState.Content.Error.Common) {
            ShowToast(message = currentState.error.toString())
        }
    }

    when (currentState) {

        is CoffeeMenuScreenState.Content ->
            Column(
                modifier = modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier
                        .padding(8.dp)
                        .weight(1F),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val items = currentState.list
                    items(items) { currentItem ->
                        MenuItem(
                            modifier = modifier,
                            item = currentItem,
                            onItemIncrease = { onItemIncrease(it) },
                            onItemDecrease = { onItemDecrease(it) },
                        )
                    }
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), onClick = { onNextScreen(id) }
                ) {
                    Text(text = stringResource(R.string.go_to_payment))
                }
            }

        CoffeeMenuScreenState.Loading ->
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        CoffeeMenuScreenState.TokenExpired -> {
            onTokenExpired()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MenuItem(
    modifier: Modifier,
    item: CoffeeMenuItem,
    onItemIncrease: (CoffeeMenuItem) -> Unit,
    onItemDecrease: (CoffeeMenuItem) -> Unit,
) {

    Column(modifier = modifier) {
        GlideSubcomposition(
            modifier = modifier,
            model = item.imageUrl,
        ) {
            when (state) {
                RequestState.Failure -> Image(
                    modifier = modifier.size(64.dp),
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null)
                RequestState.Loading -> CircularProgressIndicator(modifier = modifier)
                is RequestState.Success -> Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = modifier)
            }
        }
        Text(text = item.name)
        Row(modifier = modifier) {
            Text(text = String.format(stringResource(R.string.price), item.price))
            IconButton(
                onClick = { onItemDecrease(item) },
                enabled = (item.count != 0)
            ) {
                Icon(painter = painterResource(R.drawable.ic_minus), contentDescription = null)
            }
            Text(text = item.count.toString())
            IconButton(
                onClick = { onItemIncrease(item) },
            ) {
                Icon(painter = painterResource(R.drawable.ic_plus), contentDescription = null)
            }
        }
    }
}
