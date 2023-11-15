package com.example.tugas_room.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "date")
    val date: String
)
