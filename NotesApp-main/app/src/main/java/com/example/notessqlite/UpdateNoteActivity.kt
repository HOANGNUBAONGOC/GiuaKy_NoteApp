package com.example.notessqlite

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1
    private var selectedImageUri: Uri? = null // Lưu URI ảnh mới

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        // Nhận ID ghi chú
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }

        // Lấy ghi chú từ database
        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.decription)

        // Hiển thị ảnh cũ nếu có
        if (!note.file.isNullOrEmpty()) {
            Log.d("UpdateNoteActivity", "Loading image: ${note.file}") // Debug
            Glide.with(this)
                .load(note.file)
                .into(binding.updateImageView)
        } else {
            Log.d("UpdateNoteActivity", "No image to load")
        }


        // Chọn ảnh mới
        binding.updateSelectImageButton.setOnClickListener {
            openImagePicker()
        }

        // Lưu cập nhật
        binding.updateSaveButton.setOnClickListener {
            saveUpdatedNote()
        }
    }

    // Mở trình chọn ảnh
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            binding.updateImageView.setImageURI(uri) // Hiển thị ảnh mới
        }
    }

    private fun openImagePicker() {
        imagePickerLauncher.launch("image/*")
    }

    private fun saveUpdatedNote() {
        val newTitle = binding.updateTitleEditText.text.toString()
        val newDescription = binding.updateContentEditText.text.toString()
        val newImagePath = selectedImageUri?.toString() ?: db.getNoteByID(noteId).file // Giữ ảnh cũ nếu không chọn ảnh mới

        if (newTitle.isNotEmpty() && newDescription.isNotEmpty()) {
            val updatedNote = Note(noteId, newTitle, newDescription, newImagePath)
            db.updateNote(updatedNote)
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
