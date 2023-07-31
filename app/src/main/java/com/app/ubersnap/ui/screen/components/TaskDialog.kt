package com.app.ubersnap.ui.screen.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.app.ubersnap.R
import com.app.ubersnap.data.Task
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDialogBox(
    openDialog: MutableState<Boolean>,
    snackbarHostState: SnackbarHostState,
    title: MutableState<String>,
    description: MutableState<String>,
    dueDate: MutableState<String>,
    action: String,
    task: Task,
    onAction:(task :Task) -> Unit
) {
    val context = LocalContext.current
    val snackbarCoroutineScope = rememberCoroutineScope()

    if(openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text( stringResource(R.string.to_do)) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        placeholder = { Text(text = stringResource(R.string.enter_title)) },
                        label = { Text(text = stringResource(R.string.enter_title)) },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { description.value = it },
                        placeholder = { Text(text = stringResource(R.string.enter_description)) },
                        label = { Text(text = stringResource(R.string.enter_description)) },
                        singleLine = true
                    )
                    DatePicker(dueDate = dueDate)
                }
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        if(title.value=="") {
                            Toast.makeText(context, "Title can't be empty", Toast.LENGTH_SHORT).show()
                        } else if(dueDate.value==""){
                            Toast.makeText(context, "Please select a due date", Toast.LENGTH_SHORT).show()
                        } else {
                            val updatedTask = task.copy(
                                title = title.value,
                                description = description.value,
                                dueDate = dueDate.value
                            )
                            onAction(updatedTask)
                            if(action=="Add"){
                                title.value = ""
                                description.value = ""
                                dueDate.value = ""
                            }
                            openDialog.value = false
                            snackbarCoroutineScope.launch {
                                snackbarHostState.showSnackbar("Saved")
                            }
                        }
                    }
                ) {
                    Text(text = action)
                }
            }
        )
    }
}