package com.antsfamily.sexcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.antsfamily.sexcalendar.ui.theme.AndroidnativetemplateTheme
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.YearMonth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidnativetemplateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    val currentMonth = remember { YearMonth.now() }
                    val startMonth = remember { currentMonth.minusMonths(0) } // Adjust as needed
                    val endMonth = remember { currentMonth.plusMonths(0) } // Adjust as needed

                    val daysOfWeek = remember { daysOfWeek() }

                    val state = rememberCalendarState(
                        startMonth = startMonth,
                        endMonth = endMonth,
                        firstVisibleMonth = currentMonth,
                        firstDayOfWeek = daysOfWeek.first()
                    )

                    Box(modifier = Modifier.statusBarsPadding()) {
                        Column {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp)
                                ,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                items(daysOfWeek) { day ->
                                    Text(day.name.take(2))
                                }
                            }

                            HorizontalDivider(thickness = 8.dp, color = Color.Transparent)

                            VerticalCalendar(
                                state = state,
                                dayContent = { Day(it, currentMonth.month.value) }
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Day(day: CalendarDay, currentMonthIndex: Int) {
    Box(
        modifier = Modifier.aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        if (day.date.month.value == currentMonthIndex) {
            Text(text = day.date.dayOfMonth.toString())
        }
    }
}
