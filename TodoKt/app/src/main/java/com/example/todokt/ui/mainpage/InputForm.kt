package com.example.todokt.ui.mainpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFrom(
    sheetState: SheetState,
    datePickerState: DatePickerState,
    timePickerState: TimePickerState,
    textFieldState: MutableState<String>,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 64.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatePicker(state = datePickerState)
            TimePicker(state = timePickerState)
            DescriptionTextField(
                textFieldState = textFieldState,
                onDone = { onConfirmClick() }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onConfirmClick) {
                Text("Confirm", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun DescriptionTextField(
    textFieldState: MutableState<String>,
    onDone: KeyboardActionScope.() -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.padding(8.dp),
        value = textFieldState.value,
        label = { Text(text = "Description") },
        singleLine = true,
        onValueChange = { textFieldState.value = it },
        keyboardActions = KeyboardActions(onDone = onDone)
    )
}
