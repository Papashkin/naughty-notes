package com.antsfamily.naughtynotes.presentation.pincode.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.pincode.model.NumpadKey
import com.antsfamily.naughtynotes.presentation.pincode.model.NumpadSymbol
import com.antsfamily.naughtynotes.ui.theme.Padding

private val LINE_1 = listOf(NumpadKey.ONE, NumpadKey.TWO, NumpadKey.THREE)
private val LINE_2 = listOf(NumpadKey.FOUR, NumpadKey.FIVE, NumpadKey.SIX)
private val LINE_3 = listOf(NumpadKey.SEVEN, NumpadKey.EIGHT, NumpadKey.NINE)
private val LINE_4 = listOf(NumpadSymbol.VISIBLE, NumpadKey.ZERO, NumpadSymbol.DELETE)

@Composable
fun PinCodeKeyboard(
    modifier: Modifier = Modifier,
    isCodeVisible: Boolean,
    onKeyClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onShowCodeClick: () -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.padding(horizontal = Padding.large),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(Padding.regular),
        verticalArrangement = Arrangement.spacedBy(Padding.regular)
    ) {
        items(LINE_1) {
            KeyButton(key = it.value.toString()) {
                onKeyClick(it.value)
            }
        }
        items(LINE_2) {
            KeyButton(key = it.value.toString()) {
                onKeyClick(it.value)
            }
        }
        items(LINE_3) {
            KeyButton(key = it.value.toString()) {
                onKeyClick(it.value)
            }
        }
        items(LINE_4) {
            when (it) {
                NumpadSymbol.DELETE -> KeyButton(
                    symbol = ImageVector.vectorResource(R.drawable.ic_delete)
                ) { onDeleteClick() }

                NumpadSymbol.VISIBLE -> KeyButton(
                    symbol = if (isCodeVisible) {
                        ImageVector.vectorResource(R.drawable.ic_eye_closed)
                    } else {
                        ImageVector.vectorResource(R.drawable.ic_eye)
                    }
                ) { onShowCodeClick() }

                is NumpadKey -> KeyButton(key = it.value.toString()) {
                    onKeyClick(it.value)
                }
            }
        }
    }
}

@Composable
fun KeyButton(
    key: String? = null,
    symbol: ImageVector? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        key?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleLarge
            )
        }
        symbol?.let {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = it,
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PinCodeKeyboardPreview() {
    PinCodeKeyboard(
        isCodeVisible = false,
        onKeyClick = {},
        onShowCodeClick = {},
        onDeleteClick = {}
    )
}