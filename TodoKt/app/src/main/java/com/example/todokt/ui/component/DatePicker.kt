package com.example.todokt.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DatePickerPreview() {
    val calendar = Calendar.getInstance()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableLongStateOf(calendar.timeInMillis) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    selectedDate = datePickerState.selectedDateMillis!!
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    Button(
        onClick = {
            showDatePicker = true
        }
    ) {
        Text(text = "Show Date Picker")
    }

    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ROOT)
    Text(
        text = "Selected date: ${formatter.format(Date(selectedDate))}"
    )
}