package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.presentation.common.ShimmerLoading
import com.antsfamily.naughtynotes.ui.theme.Padding

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentLoadingView(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(Modifier.height(36.dp))

        ShimmerLoading(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 420.dp, max = 440.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.large
                ),
        )

        Spacer(Modifier.height(Padding.large))

        Column(
            verticalArrangement = Arrangement.spacedBy(Padding.regular)
        ) {
            ShimmerLoading(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                durationMillis = 1000
            )
            ShimmerLoading(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                durationMillis = 1000
            )
            ShimmerLoading(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                durationMillis = 1000
            )
        }
    }
}