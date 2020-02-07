package com.example.acto_poc.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity (
    @PrimaryKey
    val id: Int,
    val albumId: Int,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?)