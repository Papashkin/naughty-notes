package com.antsfamily.sexcalendar.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.Month
import java.time.YearMonth

@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    month: Month
) {
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = YearMonth.of(2025, month),
        endMonth = YearMonth.of(2025, month),
        firstVisibleMonth = YearMonth.of(2025, month),
        firstDayOfWeek = daysOfWeek.first()
    )

    Card(modifier = modifier
        .padding(horizontal = 8.dp, vertical = 12.dp)
        .height(400.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(verticalArrangement = Arrangement.Bottom) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items(daysOfWeek) { day ->
                    Box(contentAlignment = Alignment.Center) {
                        Text(day.name.take(2))
                    }
                }
            }

            VerticalCalendar(
                state = state,
                dayContent = {
                    Day(it, month.value)
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
    CalendarView(month = Month.MARCH)
}