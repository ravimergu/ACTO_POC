package com.example.acto_poc.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity (
    @PrimaryKey
    val id: Int,
    val name: String?,
    val userName: String?,
    val email: String?,
    val phone: String?,
    val website: String?)