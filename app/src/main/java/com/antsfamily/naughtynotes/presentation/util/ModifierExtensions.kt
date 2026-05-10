package com.antsfamily.naughtynotes.presentation.util

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Modifier.debouncedClickable(
    interactionSource: MutableInteractionSource?,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    debounceInterval: Long = 200L,
    onClick: () -> Unit
): Modifier = composed {

    val (isClickEnabled, setIsClickEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    this.clickable(
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled && isClickEnabled,
        onClickLabel = onClickLabel,
        role = role
    ) {
        if (isClickEnabled) {
            setIsClickEnabled(false)
            onClick.invoke()
            scope.launch {
                delay(debounceInterval)
                setIsClickEnabled(true)
            }
        }
    }
}


fun Modifier.debouncedClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    debounceInterval: Long = 200L,
    onClick: () -> Unit
): Modifier = composed {

    val (isClickEnabled, setIsClickEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    this.clickable(
        enabled = enabled && isClickEnabled,
        onClickLabel = onClickLabel,
        role = role
    ) {
        if (isClickEnabled) {
            setIsClickEnabled(false)
            onClick.invoke()
            scope.launch {
                delay(debounceInterval)
                setIsClickEnabled(true)
            }
        }
    }
}

fun Modifier.clickableWithoutIndication(
    action: () -> Unit
): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        action()
    }
}
