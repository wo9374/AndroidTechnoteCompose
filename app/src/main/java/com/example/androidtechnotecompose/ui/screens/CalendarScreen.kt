@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidtechnotecompose.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "")
                }
                Text(text = "Calendar", fontSize = 24.sp)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
                }
            }
        }
    }
}