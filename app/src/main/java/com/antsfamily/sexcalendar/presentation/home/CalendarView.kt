package com.antsfamily.sexcalendar.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    year: Int,
    month: Month
) {
    val daysOfWeek = remember { daysOfWeek() }
    val yearMonth = remember { YearMonth.of(year, month) }

    val state = rememberCalendarState(
        startMonth = yearMonth,
        endMonth = yearMonth,
        firstVisibleMonth = yearMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    Card(modifier = modifier
        .padding(horizontal = 8.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(verticalArrangement = Arrangement.Bottom) {

            VerticalCalendar(
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceDim),
                state = state,
                calendarScrollPaged = true,
                userScrollEnabled = true,
                dayContent = {
                    Day(it, month.value)
                },
                monthHeader = {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        items(daysOfWeek) { day ->
                            Box {
                                Text(
                                    modifier = Modifier.width(48.dp),
                                    textAlign = TextAlign.Center,
                                    text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun Day(day: CalendarDay, currentMonthIndex: Int) {
    Box(
        modifier = Modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        if (day.date.month.value == currentMonthIndex) {
           Text(text = day.date.dayOfMonth.toString())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CalendarViewPreview() {
    CalendarView(year = 2025, month = Month.MARCH)
}