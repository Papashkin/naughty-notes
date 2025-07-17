package com.antsfamily.sexcalendar.presentation.home.view

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
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val CALENDAR_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy")

@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    notes: List<NoteModel>,
    isNavigationBackVisible: Boolean,
    isNavigationForwardVisible: Boolean,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit,
) {
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = yearMonth,
        endMonth = yearMonth,
        firstVisibleMonth = yearMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(verticalArrangement = Arrangement.Bottom) {

            CalendarHeader(
                yearMonth = yearMonth,
                isNavigationBackVisible = isNavigationBackVisible,
                isNavigationForwardVisible = isNavigationForwardVisible,
                onPreviousMonthClick = onPreviousMonthClick,
                onNextMonthClick = onNextMonthClick
            )

            VerticalCalendar(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                state = state,
                dayContent = { day ->
                    Day(
                        day,
                        yearMonth.monthValue,
                        notes.filter { note -> note.date == day.date }.size
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
    isNavigationBackVisible: Boolean,
    isNavigationForwardVisible: Boolean,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { if (isNavigationBackVisible) onPreviousMonthClick.invoke() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                modifier = Modifier.size(32.dp),
                contentDescription = null,
                tint = if (isNavigationBackVisible) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
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
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                modifier = Modifier.size(32.dp),
                contentDescription = null,
                tint = if (isNavigationForwardVisible) {
                    MaterialTheme.colorScheme.onSurface
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
    currentMonthIndex: Int,
    recordsAmount: Int,
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
                onDayClick(day.date)
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        if (recordsAmount == 0) {
            if (day.date.month.value == currentMonthIndex) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        else {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    if (day.date.month.value == currentMonthIndex) {
                        Text(
                            text = day.date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary
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
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
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
        yearMonth = YearMonth.now(),
        notes = listOf(
            NoteModel(
                LocalDate.now(),
                SexType.VAGINAL,
                isProtected = true,
                painRate = 2,
                rate = 4,
                personalNote = "That was something crazy"
            )
        ),
        isNavigationBackVisible = true,
        isNavigationForwardVisible = true,
        onNextMonthClick = {},
        onPreviousMonthClick = {},
        onDayClick = {})
}