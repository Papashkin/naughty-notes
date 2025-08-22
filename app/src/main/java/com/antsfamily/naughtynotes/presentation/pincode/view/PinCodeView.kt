package com.antsfamily.naughtynotes.presentation.pincode.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.presentation.util.PIN_CODE_KEY_BULLET
import com.antsfamily.naughtynotes.presentation.util.PIN_CODE_SIZE
import com.antsfamily.naughtynotes.ui.theme.Padding


@Composable
fun PinCodeView(
    modifier: Modifier = Modifier,
    isCodeVisible: Boolean,
    code: String,
    isError: Boolean = false,
) {
    Row(
        modifier = modifier.padding(Padding.medium),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 0..<PIN_CODE_SIZE) {
            Box(
                modifier = Modifier
                    .height(64.dp)
                    .width(48.dp)
                    .clip(RoundedCornerShape(Padding.small))
                    .background(
                        color = if (code.length > i) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceContainer
                        },
                        shape = RoundedCornerShape(Padding.regular)
                    )
                    .border(
                        width = when {
                            code.length == i -> 2.dp
                            isError -> 2.dp
                            else -> 0.dp
                        },
                        color = when {
                            code.length == i -> MaterialTheme.colorScheme.primary
                            isError -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.surfaceContainer
                        },
                        shape = RoundedCornerShape(Padding.regular)
                    )
            ) {
                if (i < code.length) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = if (isCodeVisible) code[i].toString() else PIN_CODE_KEY_BULLET,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PinCodeViewPreview() {
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        PinCodeView(isCodeVisible = false, code = "2333")
        PinCodeView(isCodeVisible = true, code = "12345")
        PinCodeView(isCodeVisible = true, code = "2353")
        PinCodeView(isCodeVisible = true, code = "19")
        PinCodeView(isCodeVisible = true, code = "1980", isError = true)
    }
}