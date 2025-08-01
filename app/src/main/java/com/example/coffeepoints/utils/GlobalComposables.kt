package com.example.coffeepoints.utils

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    val context = LocalContext.current.applicationContext
    Toast.makeText(context, message, duration).show()
}