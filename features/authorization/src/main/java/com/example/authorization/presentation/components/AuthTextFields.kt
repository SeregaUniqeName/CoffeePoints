package com.example.authorization.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authorization.R

@Composable
fun RegistrationTextField(
    error: MutableState<Boolean>,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    validateInput: (String) -> Boolean,
) {

    val firstTime = rememberSaveable { mutableStateOf(true) }

    Column {
        Text(
            text = stringResource(R.string.e_mail),
            fontSize = 12.sp
        )
        Spacer(
            modifier.height(8.dp)
        )
        OutlinedTextField(
            supportingText = {
                if (error.value) {
                    Text(text = stringResource(R.string.login_format_error))
                }
            },
            modifier = modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        error.value = false
                        firstTime.value = false
                    }
                    else error.value = validateInput(value) && !firstTime.value
                },
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            value = value,
            onValueChange = { onValueChange(it) },
            isError = error.value
        )
    }
}

@Composable
fun RegistrationPasswordTextField(
    error: MutableState<Boolean>,
    errorMessage: String,
    label: String,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    validateInput: (String) -> Boolean
) {

    val firstTime = rememberSaveable { mutableStateOf(true) }

    Column {

        Text(
            text = label,
            fontSize = 12.sp
        )
        Spacer(
            modifier.height(8.dp)
        )
        OutlinedTextField(
            supportingText = {
                if (error.value) {
                    Text(text = errorMessage)
                }
            },
            modifier = modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        error.value = false
                        firstTime.value = false
                    }
                    else error.value = validateInput(value) && !firstTime.value
                },
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            value = value,
            onValueChange = { onValueChange(it) },
            visualTransformation = PasswordVisualTransformation(),
            isError = error.value
        )
    }
}

@Composable
fun RegistrationPasswordConfirmTextField(
    error: MutableState<Boolean>,
    errorMessage: String,
    label: String,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    validateInput: (String) -> Boolean
) {

    val firstTime = rememberSaveable { mutableStateOf(true) }

    Column {

        Text(
            text = label,
            fontSize = 12.sp
        )
        Spacer(
            modifier.height(8.dp)
        )
        OutlinedTextField(
            supportingText = {
                if (error.value) {
                    Text(text = errorMessage)
                }
            },
            modifier = modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        error.value = false
                        firstTime.value = false
                    }
                },
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            value = value,
            onValueChange = { onValueChange(it)
                error.value = validateInput(it) },
            visualTransformation = PasswordVisualTransformation(),
            isError = error.value && !firstTime.value
        )
    }
}

@Composable
fun LogInTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {

    Column {
        Text(
            text = stringResource(R.string.e_mail),
            fontSize = 12.sp
        )
        Spacer(
            modifier.height(8.dp)
        )
        OutlinedTextField(
            modifier = modifier,
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            value = value,
            onValueChange = { onValueChange(it) },
        )
    }
}

@Composable
fun LogInPasswordTextField(
    label: String,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {

    Column {

        Text(
            text = label,
            fontSize = 12.sp
        )
        Spacer(
            modifier.height(8.dp)
        )
        OutlinedTextField(
            modifier = modifier,
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            value = value,
            onValueChange = { onValueChange(it) },
            visualTransformation = PasswordVisualTransformation(),
        )
    }
}