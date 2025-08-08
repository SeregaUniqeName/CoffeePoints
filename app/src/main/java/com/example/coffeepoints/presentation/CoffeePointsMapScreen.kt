package com.example.coffeepoints.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffeepoints.R
import com.example.coffeepoints.getApplicationComponent
import com.example.coffeepoints.utils.ShowToast
import com.example.coffeepointsmap.models.MapEntity
import com.example.coffeepointsmap.presentation.MapItemsViewModel
import com.example.coffeepointsmap.presentation.MapScreenErrors
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun CoffeeShopMapScreen(
    paddingValues: PaddingValues,
    onNextScreen: (Int) -> Unit
) {

    val component = getApplicationComponent()
        .coffeePointsMapScreenComponentFactory().create()
    val viewModel: MapItemsViewModel = viewModel(factory = component.getViewModelsFactory())

    val locations = viewModel.pointsList.collectAsState()
    val error = viewModel.error.collectAsState()

    val mapView = MapView(LocalContext.current)

    LaunchedEffect(Unit) {
        viewModel.getAllItems()
    }

    CoffeeShopMapContent(
        modifier = Modifier,
        locationState = locations,
        mapView = mapView,
        onNextScreen = { onNextScreen(it) },
        error = error
    )
}


@Composable
fun CoffeeShopMapContent(
    modifier: Modifier,
    locationState: State<List<MapEntity>>,
    mapView: MapView,
    onNextScreen: (Int) -> Unit,
    error: State<MapScreenErrors>
) {

    if (error.value != MapScreenErrors.Empty) {
        ShowToast(error.value.toString())
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = {
            MapKitFactory.getInstance().onStart()
            mapView
        },
        update = {
            if (locationState.value.isNotEmpty()) {
                locationState.value.forEach { item ->
                    mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                        addTapListener { p0, p1 ->
                            onNextScreen(item.id)
                            true
                        }
                        geometry = Point(item.lat, item.lon)
                        setIcon(
                            ImageProvider.fromResource(
                                mapView.context,
                                R.drawable.ic_location
                            )
                        )
                    }
                    val pointsList = mutableListOf<Point>()
                    locationState.value.forEach {
                        pointsList.add(Point(it.lat, it.lon))
                    }
                    val polyline = Polyline(pointsList)
                    val geometry = Geometry.fromPolyline(polyline)
                    val position = mapView.mapWindow.map.cameraPosition(geometry)
                    mapView.mapWindow.map.move(position)
                }
            }
        },
        onRelease = {
            MapKitFactory.getInstance().onStop()
        }
    )
}

