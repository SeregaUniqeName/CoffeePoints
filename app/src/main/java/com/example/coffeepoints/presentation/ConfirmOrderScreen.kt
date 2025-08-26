package com.example.coffeepoints.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coffeepoints.R
import com.example.coffeepoints.getApplicationComponent
import com.example.confermscreen.models.OrderItemModel
import com.example.confermscreen.presentation.ConfirmScreenViewModel

@Composable
fun ConfirmOrderScreen(
    paddingValues: PaddingValues,
    id: Int,
) {

    val component = getApplicationComponent()
        .getConfirmOrderComponentFactory().create()
    val viewModel: ConfirmScreenViewModel = viewModel(factory = component.getViewModelsFactory())
    val screenState = viewModel.list.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOrder(id)
    }

    ConfirmOrderContent(
        paddingValues = paddingValues,
        modifier = Modifier,
        state = screenState
    )
}

@Composable
fun ConfirmOrderContent(
    paddingValues: PaddingValues,
    modifier: Modifier,
    state: State<List<OrderItemModel>>,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val items = state.value
            items(items) { currentItem ->
                ItemCard(
                    modifier = modifier,
                    item = currentItem
                )
            }
        }
        Text(text = "${stringResource(R.string.order_waiting_time)}\n${stringResource(R.string.gratitude)}")
        Button(onClick = {}) {
            Text(text = stringResource(R.string.pay_button))
        }
    }
}

@Composable
fun ItemCard(
    modifier: Modifier,
    item: OrderItemModel
) {

    Row(modifier = modifier) {
        Column(modifier = modifier.weight(1F)) {
            Text(text = item.name)
            Text(text = String.format(stringResource(R.string.price), item.price))
        }
        Icon(painter = painterResource(R.drawable.ic_minus), contentDescription = null)
        Text(text = item.count.toString())
        Icon(painter = painterResource(R.drawable.ic_plus), contentDescription = null)
    }

}