package com.app.ubersnap.ui.screen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.ubersnap.R
import com.app.ubersnap.data.Task
import com.app.ubersnap.viewmodel.TodoViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(task: Task, todoViewModel: TodoViewModel, snackbarHostState: SnackbarHostState) {
    var expanded by remember { mutableStateOf(false) }
    val title = remember { mutableStateOf(task.title) }
    val description = remember { mutableStateOf(task.description) }
    val dueDate = remember { mutableStateOf(task.dueDate) }
    val openDialog = remember { mutableStateOf(false) }
    val figtreeSemiBold = FontFamily(Font(R.font.figtree_semi_bold))
    val figtreeRegular = FontFamily(Font(R.font.figtree_regular))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 4.dp)
    ){
        Column(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val checkedState = remember { mutableStateOf(task.completed==1) }
                Checkbox(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        val updatedTask = task.copy(completed = if(checkedState.value) 1 else 0)
                        todoViewModel.updateTask(updatedTask)
                    }
                )
                Text(text = task.title, fontSize = 25.sp, fontFamily = figtreeSemiBold, modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { expanded = true }
                ) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = stringResource(R.string.more_actions))
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text( stringResource(R.string.edit)) },
                            leadingIcon = { Icon(imageVector = Icons.Default.Edit, contentDescription = stringResource(R.string.edit)) },
                            onClick = {
                                expanded = false
                                openDialog.value = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text( stringResource(R.string.delete)) },
                            leadingIcon = { Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete)) },
                            onClick = {
                                expanded = false
                                todoViewModel.deleteTask(task)
                            }
                        )
                    }
                }
            }
            Text("Due date: ${dueDate.value}", fontFamily = figtreeRegular, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(12.dp, 0.dp, 16.dp, 8.dp))
            if(task.description!=""){
                Text(text = "Description: ${task.description}", fontFamily = figtreeRegular, modifier = Modifier.padding(12.dp, 0.dp, 16.dp, 8.dp))
            }
            TaskDialogBox(
                openDialog = openDialog,
                snackbarHostState = snackbarHostState,
                title = title,
                description = description,
                dueDate = dueDate,
                stringResource(R.string.save),
                task,
                onAction = { todoViewModel.updateTask(it) }
            )
        }
    }
}