package com.app.ubersnap.ui.screen.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.app.ubersnap.R
import com.app.ubersnap.viewmodel.TodoViewModel

@Composable
fun DeleteAlert(todoViewModel: TodoViewModel, selectedTaskType: Int, deleteAlert: MutableState<Boolean>) {
    val deleteTaskType = when(selectedTaskType){
        0 -> {"all"}
        1 -> {"all completed"}
        else -> {"all pending"}
    }
    AlertDialog(
        text = {
            Text(
                stringResource(R.string.delete_tasks, deleteTaskType),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.figtree_semi_bold))
            )
        },
        onDismissRequest = { deleteAlert.value = false },
        confirmButton = {
            OutlinedButton(
                onClick = {
                    when(selectedTaskType){
                        0 -> { todoViewModel.deleteAll()}
                        1 -> { todoViewModel.deleteCompleted()}
                        2 -> { todoViewModel.deletePendingTasks()}
                    }
                    deleteAlert.value = false
                }
            ) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { deleteAlert.value = false }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}