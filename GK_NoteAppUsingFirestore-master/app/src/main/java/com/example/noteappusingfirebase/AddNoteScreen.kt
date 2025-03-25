package com.example.noteappusingfirebase

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddNoteScreen(viewModel: NoteViewModel, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            "Add Note",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFD81B60)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFFEBEE),
                        unfocusedContainerColor = Color(0xFFFCE4EC),
                        focusedIndicatorColor = Color(0xFFD81B60),
                        unfocusedIndicatorColor = Color(0xFFF06292)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFFEBEE),
                        unfocusedContainerColor = Color(0xFFFCE4EC),
                        focusedIndicatorColor = Color(0xFFD81B60),
                        unfocusedIndicatorColor = Color(0xFFF06292)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.addNote(title, description)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD81B60),
                contentColor = Color.White
            )
        ) {
            Text("Save")
        }
    }
}
