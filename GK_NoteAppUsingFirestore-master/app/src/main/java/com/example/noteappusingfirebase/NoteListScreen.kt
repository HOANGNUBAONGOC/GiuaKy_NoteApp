package com.example.noteappusingfirebase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NoteListScreen(viewModel: NoteViewModel, navController: NavController) {
    val notes by viewModel.notes.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredNotes = notes.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "\uD83C\uDF80 NOTE APP \uD83C\uDF80",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = Color(0xFFD81B60),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search notes...") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD81B60),
                    unfocusedBorderColor = Color(0xFFF48FB1)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(filteredNotes) { note ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { navController.navigate("edit_note/${note.id}") }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFFD81B60)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = note.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF880E4F)
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("add_note") },
            containerColor = Color(0xFFD81B60), // Hồng đậm
            contentColor = Color.White, // Màu chữ trắng
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(18.dp)
        ) {
            Text("+", style = MaterialTheme.typography.headlineMedium)
        }
    }
}
