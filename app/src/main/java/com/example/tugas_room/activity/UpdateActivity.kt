package com.example.tugas_room.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugas_room.R
import com.example.tugas_room.database.Note
import com.example.tugas_room.database.NoteDao
import com.example.tugas_room.database.NoteDatabase
import com.example.tugas_room.databinding.ActivityUpdateBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.properties.Delegates

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBinding
    private var id: Int = 0
    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        id = bundle!!.getInt("EXT_ID")!!
        val title = bundle!!.getString("EXT_TITLE")!!
        val description = bundle!!.getString("EXT_DESCRIPTION")!!
        val date = bundle!!.getString("EXT_DATE")!!

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        with(binding) {
            etTitle.setText(title)
            etDescription.setText(description)
            etDate.setText(date)

            btnUpdate.setOnClickListener {
                update(
                    Note(
                        id = id,
                        title = etTitle.text.toString(),
                        description = etDescription.text.toString(),
                        date = etDate.text.toString()
                    )
                )
                id = 0
                finish()
            }

            btnDelete.setOnClickListener {
                delete(
                    Note(
                        id = id,
                        title = title,
                        description = description,
                        date = date
                    )
                )
                id = 0
                finish()
            }
        }
    }

    private fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }

    private fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }
}