@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidtechnotecompose.ui.screens

import android.widget.CalendarView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.androidtechnotecompose.R
import java.util.Calendar

@Composable
fun CalendarScreen() {

    val context = LocalContext.current

    val calendar = Calendar.getInstance()


    /*val dateState = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,{ view, year, month, dayOfMonth ->
            dateState.value = "${year}년 ${month + 1}월 ${dayOfMonth}일"
        },
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )
    datePickerDialog.show()


    val timeState = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context, { view, hourOfDay, minute ->
            timeState.value = "${hourOfDay}시 ${minute}분"
        },
        calendar[Calendar.HOUR_OF_DAY],
        calendar[Calendar.MINUTE], false
    )
    timePickerDialog.show()*/

    var date by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("")
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val calTheme = if (isSystemInDarkTheme()) R.style.CustomCalendarDark else R.style.CustomCalendar
            val dateTheme = if (isSystemInDarkTheme()) R.style.CustomDateDark else R.style.CustomDate
            val weekTheme = if (isSystemInDarkTheme()) R.style.CustomWeekDark else R.style.CustomWeek

            AndroidView(
                factory = {
                    CalendarView(android.view.ContextThemeWrapper(it, calTheme)).apply {
                        dateTextAppearance = dateTheme
                        weekDayTextAppearance = weekTheme
                    }
                },
                update = {
                    it.setOnDateChangeListener { calendarView, year, month, day ->
                        date = "$year - ${month + 1} - $day"
                    }
                })
            Text(text = date)
        }
    }
}