package com.antsfamily.sexcalendar.presentation.createnote

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.createnote.model.LoadingButton
import com.antsfamily.sexcalendar.presentation.createnote.model.SexType
import com.antsfamily.sexcalendar.presentation.createnote.model.toStringId
import com.antsfamily.sexcalendar.presentation.home.TopBar
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading

@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.navigateBackEvent.collect {
            onNavigateBack()
        }
    }

    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is CreateNoteUiState.Loading -> FullScreenLoading()
        is CreateNoteUiState.Content -> CreateNoteContent(
            uiState,
            setSexType = { viewModel.setSexType(it) },
            setIsProtected = { viewModel.setIsProtected(it) },
            setPainRate = { viewModel.setPainRate(it) },
            setPleasureRate = { viewModel.setPleasureRate(it) },
            setNote = { viewModel.setNote(it) },
            onSaveButtonClicked = { viewModel.onSaveButtonClicked() },
            onNavigateBack = onNavigateBack
        )
    }
}

@Composable
fun CreateNoteContent(
    state: CreateNoteUiState.Content,
    setSexType: (SexType) -> Unit,
    setIsProtected: (Boolean) -> Unit,
    setPainRate: (Int) -> Unit,
    setPleasureRate: (Int) -> Unit,
    setNote: (String) -> Unit,
    onSaveButtonClicked: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val (isSexTypeExpanded, setIsSexTypeExpanded) = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
//            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surface)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TopBar(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .fillMaxWidth(),
                title = "Create a Note",
                onNavigationBack = {
                    onNavigateBack()
                }
            )

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Box(
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 8.dp)
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        label = { Text("Type of sex") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable { setIsSexTypeExpanded(!isSexTypeExpanded) }
                            )
                        },
                        value = stringResource(state.type.toStringId()),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                setIsSexTypeExpanded(!isSexTypeExpanded)
                            },
                        onValueChange = {
                            setIsSexTypeExpanded(!isSexTypeExpanded)
                        }
                    )
                    DropdownMenu(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = isSexTypeExpanded,
                        onDismissRequest = { setIsSexTypeExpanded(false) }
                    ) {
                        SexType.entries.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(text = stringResource(it.toStringId()))
                                },
                                modifier = Modifier.fillMaxSize(),
                                onClick = {
                                    setSexType(it)
                                    setIsSexTypeExpanded(false)
                                }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                ) {
                    Text("Was it protected sex?", modifier = Modifier.padding(vertical = 4.dp))
                    Switch(
                        checked = state.isProtected,
                        onCheckedChange = { setIsProtected(it) }
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                ) {
                    Text("Was it painful?", modifier = Modifier.padding(vertical = 4.dp))
                    RatingBar(
                        rating = state.painRate,
                        selectedIcon = ImageVector.vectorResource(R.drawable.ic_pain),
                        defaultIcon = ImageVector.vectorResource(R.drawable.ic_pain_outlined),
                        onRatingChanged = { setPainRate(it) }
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                ) {
                    Text("Rate your pleasure", modifier = Modifier.padding(vertical = 4.dp))
                    RatingBar(
                        rating = state.rate,
                        selectedIcon = Icons.Default.Favorite,
                        defaultIcon = Icons.Default.FavoriteBorder,
                        onRatingChanged = { setPleasureRate(it) }
                    )
                }

                OutlinedTextField(
                    label = {
                        Text("Note (optional)")
                    },
                    value = state.note,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    onValueChange = {
                        setNote(it)
                    },
                    minLines = 4,
                    supportingText = {
                        Text("${state.note.length}/60")
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            LoadingButton(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                onClick = { onSaveButtonClicked() },
                loading = state.isSaveButtonLoadingVisible,
                enabled = state.isSaveButtonEnabled,
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starCount: Int = 5,
    spacing: Dp = 8.dp,
    rating: Int,
    selectedIcon: ImageVector,
    defaultIcon: ImageVector,
    onRatingChanged: (Int) -> Unit,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spacing)) {
        for (i in 1..starCount) {
            Icon(
                imageVector = if (i <= rating) selectedIcon else defaultIcon,
                contentDescription = "Star $i",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateNoteContentPreview(onNavigateBack: () -> Unit) {
    CreateNoteContent(
        state = CreateNoteUiState.Content.Default,
        {}, {}, {}, {}, {}, {}, {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RatingBarPreview() {
    Column {

        RatingBar(
            rating = 0,
            selectedIcon = Icons.Default.Favorite,
            defaultIcon = Icons.Default.FavoriteBorder,
            onRatingChanged = {}
        )
        RatingBar(
            rating = 0,
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_pain),
            defaultIcon = ImageVector.vectorResource(R.drawable.ic_pain_outlined),
            onRatingChanged = {}
        )
    }
}