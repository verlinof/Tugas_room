package com.example.tugas_room.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugas_room.R
import com.example.tugas_room.database.Note
import com.example.tugas_room.database.NoteDao
import com.example.tugas_room.database.NoteDatabase
import com.example.tugas_room.databinding.ActivityUploadBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UploadActivity : AppCompatActivity() {
    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService

    private lateinit var binding: ActivityUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        binding.btnCreate.setOnClickListener{
            insert(
                Note(
                    title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                    date = binding.etDate.text.toString()
                )
            )
            finish()
        }
    }

    private fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }
}