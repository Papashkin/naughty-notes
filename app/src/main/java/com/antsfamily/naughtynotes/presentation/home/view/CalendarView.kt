package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.ui.theme.Padding
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private const val CALENDAR_VIEW_MONTH_AMOUNT = 12L

@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    datesWithNotes: List<LocalDate>,
    onMonthChanged: (YearMonth) -> Unit,
    onDayClick: (LocalDate) -> Unit,
) {
    val currentDay = remember { LocalDate.now() }
    val daysOfWeek = remember { daysOfWeek() }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Padding.regular))
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        val state = rememberCalendarState(
            startMonth = yearMonth.minusMonths(CALENDAR_VIEW_MONTH_AMOUNT),
            endMonth = yearMonth.plusMonths(CALENDAR_VIEW_MONTH_AMOUNT),
            firstVisibleMonth = yearMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfGrid
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state) {
            onMonthChanged(it)
        }

        CalendarTitle(
            modifier = Modifier.padding(vertical = Padding.regular, horizontal = Padding.small),
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        HorizontalCalendar(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    currentDay = currentDay,
                    isWithRecords = day.date in datesWithNotes
                ) {
                    onDayClick(it)
                }
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )
    }
}

@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    currentDay: LocalDate,
    isWithRecords: Boolean,
    onClick: (LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(Padding.x_small)
            .clip(RoundedCornerShape(Padding.small))
            .background(
                color = if (day.position == DayPosition.MonthDate && day.date == currentDay) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick(day.date) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = when (day.position) {
                DayPosition.MonthDate -> when {
                    (day.date == currentDay) -> MaterialTheme.colorScheme.onPrimary
                    else -> MaterialTheme.colorScheme.onSurface
                }
                else -> MaterialTheme.colorScheme.surfaceContainer
            },
        )


        if (isWithRecords) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_heart_filled),
                tint = if (day.date == currentDay) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.primary
                },
                contentDescription = null,
                modifier = Modifier
                    .padding(Padding.tiny)
                    .size(12.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun CalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .testTag("MonthTitle"),
            text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()).plus(" ")
                .plus(currentMonth.year),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
        )
        CalendarNavigationIcon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
private fun CalendarNavigationIcon(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(Padding.x_small)
            .align(Alignment.Center),
        imageVector = imageVector,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = contentDescription,
    )
}

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
    onMonthChanged: (YearMonth) -> Unit
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month ->
                onMonthChanged(month.yearMonth)
                visibleMonth.value = month
            }
    }
    return visibleMonth.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CalendarViewPreview() {
    CalendarView(
        yearMonth = YearMonth.of(2025, Month.AUGUST),
        datesWithNotes = listOf(
            LocalDate.of(2025, Month.JULY, 19),
            LocalDate.of(2025, Month.JULY, 21),
            LocalDate.of(2025, Month.JULY, 25),
            LocalDate.now(),
        ),
        onMonthChanged = {},
        onDayClick = {})
}