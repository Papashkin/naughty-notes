package com.antsfamily.naughtynotes.presentation.noteform.model

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 4.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Box {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoadingButtonPreview1() {
    LoadingButton({}) {
        Text("Create account")
    }
}

@Preview
@Composable
private fun LoadingButtonPreview2() {
    LoadingButton({}, enabled = false) {
        Text("Create account")
    }
}

@Preview
@Composable
private fun LoadingButtonPreview3() {
    LoadingButton({}, loading = true) {
        Text("Create account")
    }
}