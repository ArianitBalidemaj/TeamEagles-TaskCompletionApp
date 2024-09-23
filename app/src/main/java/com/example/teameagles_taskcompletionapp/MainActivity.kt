package com.example.teameagles_taskcompletionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.teameagles_taskcompletionapp.ui.theme.TeamEaglesTaskCompletionAppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.foundation.lazy.LazyColumn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TeamEaglesTaskCompletionAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskCompletionApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Task(val description: String, var isComplete: Boolean = false)

@Composable
fun TaskCompletionApp(modifier: Modifier = Modifier) {
    var taskDescription by remember { mutableStateOf("") }
    val taskList = remember { mutableStateListOf<Task>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Task Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                taskList.add(Task(taskDescription))
                taskDescription = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        LazyColumn {
            items(taskList.size) { index ->
                val task = taskList[index]
                TaskRow(task = task, onTaskCheckedChange = { isChecked ->
                    taskList[index] = task.copy(isComplete = isChecked)
                })
            }
        }

        Button(
            onClick = {
                taskList.removeAll { it.isComplete }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Clear Completed Tasks")
        }
    }
}

@Composable
fun TaskRow(task: Task, onTaskCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            text = task.description,
            textDecoration = if (task.isComplete) TextDecoration.LineThrough else TextDecoration.None,
        )

        Checkbox(
            checked = task.isComplete,
            onCheckedChange = { onTaskCheckedChange(it) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TeamEaglesTaskCompletionAppTheme {
        TaskCompletionApp()
    }
}