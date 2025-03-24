package com.example.notessqlite

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.notessqlite.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var selectedImageUri: Uri? = null // Lưu URI ảnh đã chọn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        // Mở bộ chọn ảnh
        binding.selectImageButton.setOnClickListener {
            openImagePicker()
        }

        // Lưu ghi chú
        binding.saveButton.setOnClickListener {
            saveNote()
        }
    }

    // Launcher để chọn ảnh
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            binding.imageView.setImageURI(uri) // Hiển thị ảnh đã chọn
        }
    }

    // Mở trình chọn ảnh
    private fun openImagePicker() {
        imagePickerLauncher.launch("image/*")
    }

    // Lưu ghi chú vào database
    private fun saveNote() {
        val title = binding.titleEditText.text.toString()
        val description = binding.contentEditText.text.toString()
        val imagePath = selectedImageUri?.toString() // Lưu URI ảnh

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val note = Note(0, title, description, imagePath)
            db.insertNote(note)
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
