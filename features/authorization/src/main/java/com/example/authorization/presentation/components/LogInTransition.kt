package com.example.authorization.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogInTransition(
    modifier: Modifier,
    onButtonClick: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Уже зарегестрированы?",
            fontSize = 12.sp
        )
        Spacer(modifier = modifier.width(1.dp))
        TextButton(onClick = onButtonClick) {
            Text(
                text = "Войти",
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LogInTransitionPreview() {
    LogInTransition(
        modifier = Modifier,
        onButtonClick = {},
    )
}