package com.antsfamily.naughtynotes.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DebouncedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    debounceInterval: Long = 300L,
    content: @Composable RowScope.() -> Unit
) {
    val (isClickEnabled, setIsClickEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    Button(
        modifier = modifier,
        onClick = {
            if (isClickEnabled) {
                setIsClickEnabled(false)
                onClick.invoke()
            }
            scope.launch {
                delay(debounceInterval)
                setIsClickEnabled(true)
            }
        }
    ) {
        content()
    }
}

@Composable
fun DebouncedTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    debounceInterval: Long = 300L,
    content: @Composable RowScope.() -> Unit
) {
    val (isClickEnabled, setIsClickEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    TextButton(
        modifier = modifier,
        onClick = {
            if (isClickEnabled) {
                setIsClickEnabled(false)
                onClick.invoke()
            }
            scope.launch {
                delay(debounceInterval)
                setIsClickEnabled(true)
            }
        }
    ) {
        content()
    }
}