package com.example.todokt.ui.mainpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todokt.AppViewModelProvider
import com.example.todokt.database.TodoItemEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainPage() {
    val viewModel: TodoViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val switchDrawer: () -> Unit = {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedTodoItem: TodoItemEntity? = null

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = { LeftNavigationDrawer() }) {
        Scaffold(
            topBar = { MainPageTopBar(onClick = { switchDrawer() }) },
            floatingActionButton = {
                MainPageFloatingButton(onClick = {
                    showBottomSheet = true
                })
            }) {

            LazyColumn(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
            ) {
                items(viewModel.todoItemList) { item ->
                    val date = Date(item.dateMillis)
                    val format = SimpleDateFormat("dd / MM", Locale.ROOT)
                    TodoCard(
                        date = format.format(date),
                        time = "${item.hour}:${item.minute}",
                        description = item.description,
                        onLongClick = {
                            showDeleteDialog = true
                            selectedTodoItem = item
                        }
                    )
                }
            }

            if (showBottomSheet) {
                val calendar: Calendar = Calendar.getInstance()
                val datePickerState: DatePickerState =
                    rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
                val timePickerState: TimePickerState = rememberTimePickerState()
                val textFieldState = remember { mutableStateOf("") }

                InputFrom(
                    sheetState = sheetState,
                    datePickerState = datePickerState,
                    timePickerState = timePickerState,
                    textFieldState = textFieldState,
                    onDismissRequest = { showBottomSheet = false },
                    onConfirmClick = {
                        scope.launch {
                            viewModel.addTodoItem(
                                TodoItemEntity(
                                    dateMillis = datePickerState.selectedDateMillis!!,
                                    hour = timePickerState.hour,
                                    minute = timePickerState.minute,
                                    is24hour = timePickerState.is24hour,
                                    description = textFieldState.value
                                )
                            )
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    },
                )
            }

            if (showDeleteDialog) {
                DeleteDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                    },
                    onConfirmation = {
                        scope.launch {
                            viewModel.deleteTodoItem(selectedTodoItem!!)
                            selectedTodoItem = null
                        }.invokeOnCompletion {
                            showDeleteDialog = false
                        }
                    }
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageTopBar(onClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("Todo") },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(Icons.Filled.Menu, contentDescription = "")
            }
        }
    )
}

@Composable
fun MainPageFloatingButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier.offset((-16).dp, (-16).dp),
        expanded = false,
        text = { },
        icon = { Icon(Icons.Filled.Add, contentDescription = "") },
        onClick = onClick
    )
}

@Composable
fun DeleteDialog(onDismissRequest: () -> Unit, onConfirmation: () -> Unit) {
    AlertDialog(
        title = { Text(text = "Delete this?") },
        text = { Text(text = "") },
        onDismissRequest = onDismissRequest,
        confirmButton = { TextButton(onClick = onConfirmation) { Text("Yes") } },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("No") } }
    )
}
