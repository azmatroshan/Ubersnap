package com.app.ubersnap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.app.ubersnap.ui.screen.TodoListScreen
import com.app.ubersnap.ui.theme.TodoListAppTheme
import com.app.ubersnap.viewmodel.TodoViewModel


class MainActivity : ComponentActivity() {
    private val todoViewModel: TodoViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TodoListScreen(todoViewModel)
                    }
                }
            }
        }
    }
}