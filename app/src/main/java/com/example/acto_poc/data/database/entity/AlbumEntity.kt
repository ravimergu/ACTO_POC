package com.example.acto_poc.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumEntity (
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String
)