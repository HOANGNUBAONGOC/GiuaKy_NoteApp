package com.example.notessqlite

data class Note(
    val id: Int,
    val title: String,
    val decription: String,
    val file: String? = null
)
