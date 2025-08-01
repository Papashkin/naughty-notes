package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.ui.theme.Padding
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val CALENDAR_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy")

@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    datesWithNotes: List<LocalDate>,
    isNavigationForwardVisible: Boolean,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit,
) {
    val daysOfWeek = remember { daysOfWeek() }
    val currentDay = remember { LocalDate.now() }

    val state = rememberCalendarState(
        startMonth = yearMonth,
        endMonth = yearMonth,
        firstVisibleMonth = yearMonth,
        firstDayOfWeek = daysOfWeek.first(),
        outDateStyle = OutDateStyle.EndOfGrid
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Padding.regular),
    ) {
        Column(verticalArrangement = Arrangement.Bottom) {

            CalendarHeader(
                yearMonth = yearMonth,
                isNavigationForwardVisible = isNavigationForwardVisible,
                onPreviousMonthClick = onPreviousMonthClick,
                onNextMonthClick = onNextMonthClick
            )

            VerticalCalendar(
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
                state = state,
                dayContent = { day ->
                    Day(
                        day,
                        currentDay,
                        yearMonth.monthValue,
                        day.date in datesWithNotes
                    ) {
                        onDayClick(it)
                    }
                },
                monthHeader = {
                    WeekDayHeader(daysOfWeek)
                }
            )
        }
    }
}

@Composable
fun CalendarHeader(
    yearMonth: YearMonth,
    isNavigationForwardVisible: Boolean,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { onPreviousMonthClick.invoke() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                modifier = Modifier.size(32.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                yearMonth.format(CALENDAR_DATE_FORMAT).toString(),
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
        }

        IconButton(
            onClick = { if (isNavigationForwardVisible) onNextMonthClick.invoke() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                modifier = Modifier.size(32.dp),
                contentDescription = null,
                tint = if (isNavigationForwardVisible) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    currentDay: LocalDate,
    currentMonthIndex: Int,
    isWithRecords: Boolean,
    onDayClick: (LocalDate) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (day.date.monthValue == currentMonthIndex) {
                    onDayClick(day.date)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Card(modifier = Modifier.padding(Padding.x_small)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = if (day.date == currentDay && day.date.monthValue == currentMonthIndex) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceContainer
                        },
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (day.date.monthValue == currentMonthIndex) {
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            (day.date == currentDay) -> MaterialTheme.colorScheme.onPrimary
                            (day.date.monthValue == currentMonthIndex) -> MaterialTheme.colorScheme.onSurface
                            else -> MaterialTheme.colorScheme.surface
                        }
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
        }
    }
}

@Composable
fun WeekDayHeader(daysOfWeek: List<DayOfWeek>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(daysOfWeek) { day ->
            Box {
                Text(
                    modifier = Modifier.width(48.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CalendarViewPreview() {
    CalendarView(
        yearMonth = YearMonth.of(2025, Month.FEBRUARY),
        datesWithNotes = listOf(
            LocalDate.of(2025, Month.JULY, 19),
            LocalDate.of(2025, Month.JULY, 21),
            LocalDate.of(2025, Month.JULY, 25),
            LocalDate.now(),
        ),
        isNavigationForwardVisible = true,
        onNextMonthClick = {},
        onPreviousMonthClick = {},
        onDayClick = {})
}