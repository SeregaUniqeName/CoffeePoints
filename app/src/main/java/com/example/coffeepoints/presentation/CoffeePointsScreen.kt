package com.example.coffeepoints.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffeepoints.getApplicationComponent
import com.example.coffeepoints.utils.ShowToast
import com.example.coffeepointslist.models.CoffeePointEntity
import com.example.coffeepointslist.presentation.CoffeePointsScreenState
import com.example.coffeepointslist.presentation.CoffeePointsViewModel
import com.example.core.utils.MyLocationManager
import com.example.coffeepoints.R

@SuppressLint("MissingPermission")
@Composable
fun CoffeePointsScreen(
    paddingValues: PaddingValues,
    onNextScreen: (CoffeePointEntity) -> Unit,
    onMapScreen: () -> Unit,
    onTokenExpired: () -> Unit,
    locationManager: MyLocationManager
) {

    val component = getApplicationComponent()
        .coffeePointsScreenComponentFactory().create()
    val viewModel: CoffeePointsViewModel = viewModel(factory = component.getViewModelsFactory())
    val screenState = viewModel.screenState.collectAsState()
    val lat = locationManager.lat.collectAsState()
    val lon = locationManager.lon.collectAsState()

    LaunchedEffect(Unit) {
        locationManager.getCurrentLocation()
    }

    CoffeePointsContent(
        modifier = Modifier,
        screenState = screenState,
        paddingValues = paddingValues,
        onDataLoad = { viewModel.getPoints(lat.value, lon.value) },
        onNextScreen = { onNextScreen(it) },
        onTokenExpired = onTokenExpired,
        onMapScreen = onMapScreen
    )

}

@Composable
fun CoffeePointsContent(
    modifier: Modifier,
    screenState: State<CoffeePointsScreenState>,
    paddingValues: PaddingValues,
    onDataLoad: () -> Unit,
    onNextScreen: (CoffeePointEntity) -> Unit,
    onTokenExpired: () -> Unit,
    onMapScreen: () -> Unit,
) {

    val currentState = screenState.value

    when (currentState) {
        is CoffeePointsScreenState.Error -> {
            ShowToast(currentState.message)
        }

        is CoffeePointsScreenState.Loaded -> {
            Column {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val items = currentState.coffeePointsList
                    items(items) { currentItem ->
                        CoffeePointCard(
                            modifier = modifier,
                            item = currentItem,
                            onNextScreen = { onNextScreen(it) }
                        )
                    }
                }
            }

            Button(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), onClick = onMapScreen) {
                Text(text = stringResource(R.string.on_map))
            }

        }

        CoffeePointsScreenState.Loading -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            LaunchedEffect(Unit) { onDataLoad() }
        }

        CoffeePointsScreenState.TokenExpired -> {
            onTokenExpired()
        }
    }
}

@Composable
fun CoffeePointCard(
    modifier: Modifier,
    item: CoffeePointEntity,
    onNextScreen: (CoffeePointEntity) -> Unit
) {

    Card(
        modifier = modifier.clickable {
            onNextScreen(item)
        }
    ) {
        Column {
            Text(text = item.name)
            Text(text = String.format(stringResource(R.string.distance), item.distance))
        }
    }

}