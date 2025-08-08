package com.example.coffeepoints.presentation

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffeepoints.R
import com.example.coffeepoints.getApplicationComponent
import com.example.coffeepoints.utils.ShowToast
import com.example.coffeepointslist.models.CoffeePointEntity
import com.example.coffeepointslist.presentation.CoffeePointsScreenState
import com.example.coffeepointslist.presentation.CoffeePointsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CoffeePointsScreen(
    paddingValues: PaddingValues,
    onNextScreen: (Int) -> Unit,
    onMapScreen: () -> Unit,
    onTokenExpired: () -> Unit,
) {

    val component = getApplicationComponent()
        .coffeePointsScreenComponentFactory().create()
    val viewModel: CoffeePointsViewModel = viewModel(factory = component.getViewModelsFactory())
    val screenState = viewModel.screenState.collectAsState()

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val context = LocalContext.current.applicationContext
    val locationState = rememberSaveable { mutableStateOf<Pair<Double, Double>?>(null) }

    if (locationPermissionState.status.isGranted) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                locationState.value = Pair(location.latitude, location.longitude)
            }
        }
    } else {
        LaunchedEffect(Unit) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    CoffeePointsContent(
        modifier = Modifier,
        screenState = screenState,
        paddingValues = paddingValues,
        onNextScreen = { onNextScreen(it) },
        onTokenExpired = onTokenExpired,
        onMapScreen = onMapScreen,
        onDataLoad = { viewModel.getPoints(locationState.value?.first, locationState.value?.second) },
        locationState = locationState
    )
}

@Composable
fun CoffeePointsContent(
    modifier: Modifier,
    screenState: State<CoffeePointsScreenState>,
    paddingValues: PaddingValues,
    onNextScreen: (Int) -> Unit,
    onDataLoad: () -> Unit,
    onTokenExpired: () -> Unit,
    onMapScreen: () -> Unit,
    locationState: State<Pair<Double, Double>?>
) {

    val currentState = screenState.value

    LaunchedEffect(locationState.value) {
        onDataLoad()
    }

    when (currentState) {
        is CoffeePointsScreenState.Error -> {
            ShowToast(currentState.message)
        }

        is CoffeePointsScreenState.Loaded -> {
            Column {
                LazyColumn(
                    modifier = modifier
                        .padding(8.dp),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val items = currentState.coffeePointsList
                    items(items) { currentItem ->
                        CoffeePointCard(
                            modifier = modifier,
                            item = currentItem,
                            onNextScreen = { onNextScreen(it.id) },
                        )
                    }
                }
                Button(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), onClick = onMapScreen
                ) {
                    Text(text = stringResource(R.string.on_map))
                }
            }
        }

        CoffeePointsScreenState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
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
    onNextScreen: (CoffeePointEntity) -> Unit,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onNextScreen(item)
            }
    ) {
        Column {
            Text(text = item.name)
            Text(text = String.format(stringResource(R.string.distance), item.distance))
        }
    }
}
