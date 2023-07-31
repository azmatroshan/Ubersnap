package com.app.ubersnap.ui.screen

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.ubersnap.R
import com.app.ubersnap.data.Task
import com.app.ubersnap.ui.screen.components.DeleteAlert
import com.app.ubersnap.ui.screen.components.ExposedDropdownMenuBox
import com.app.ubersnap.ui.screen.components.TaskDialogBox
import com.app.ubersnap.ui.screen.components.TaskItem
import com.app.ubersnap.viewmodel.TodoViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(todoViewModel: TodoViewModel) {
    val tasks by todoViewModel.allTasks.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val dueDate =  remember { mutableStateOf("") }
    val task = Task(title = title.value, description = description.value, dueDate = dueDate.value)
    val selectedTaskType = remember { mutableStateOf(0) }
    val deleteAlert = remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                actions = {
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Info", tint = Color.White)
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.github)) },
                                onClick = {
                                    val urlIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://github.com/azmatroshan" )
                                    )
                                    context.startActivity(urlIntent)
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.linkedin)) },
                                onClick = {
                                    val urlIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://www.linkedin.com/in/azmatroshan/" )
                                    )
                                    context.startActivity(urlIntent)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            val openDialog = remember { mutableStateOf(false) }
            FloatingActionButton(onClick = { openDialog.value = true }) {
                TaskDialogBox(
                    openDialog = openDialog,
                    snackbarHostState = snackbarHostState,
                    title = title,
                    description = description,
                    dueDate = dueDate,
                    stringResource(R.string.add),
                    task,
                    onAction = { todoViewModel.insertTask(it) }
                )
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
            }
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ExposedDropdownMenuBox( selectedTaskType )
                IconButton(
                    onClick = {
                        deleteAlert.value = true
                    },
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                    if(deleteAlert.value){
                        DeleteAlert( todoViewModel = todoViewModel, selectedTaskType = selectedTaskType.value, deleteAlert = deleteAlert)
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = 12.dp)
            ){
                items(tasks) { task ->
                    when (selectedTaskType.value) {
                        0 -> TaskItem(task = task, todoViewModel = todoViewModel, snackbarHostState = snackbarHostState)
                        1 -> {
                            if (task.completed == 1) {
                                TaskItem(task = task, todoViewModel = todoViewModel, snackbarHostState = snackbarHostState)
                            }
                        }
                        2 -> {
                            if (task.completed == 0) {
                                TaskItem(task = task, todoViewModel = todoViewModel, snackbarHostState = snackbarHostState)
                            }
                        }
                    }
                }
            }
        }
    }
}