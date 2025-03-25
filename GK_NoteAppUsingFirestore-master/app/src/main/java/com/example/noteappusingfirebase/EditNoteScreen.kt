package com.example.noteappusingfirebase

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EditNoteScreen(viewModel: NoteViewModel, navController: NavController, noteId: String) {
    val notes by viewModel.notes.collectAsState()
    val note = notes.find { it.id == noteId } ?: return

    var title by remember { mutableStateOf(note.title) }
    var description by remember { mutableStateOf(note.description) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            "Edit Note",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFD81B60)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC)) // Hồng pastel
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    viewModel.updateNote(note.copy(title = title, description = description))
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD81B60),
                    contentColor = Color.White
                )
            ) {
                Text("Save")
            }

            Button(
                onClick = {
                    viewModel.deleteNote(note.id)
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC62828),
                    contentColor = Color.White
                )
            ) {
                Text("Delete")
            }
        }
    }
}
