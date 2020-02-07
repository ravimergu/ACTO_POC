package com.example.acto_poc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.acto_poc.data.database.dao.AlbumDao
import com.example.acto_poc.data.database.dao.PhotoDao
import com.example.acto_poc.data.database.dao.UserDao
import com.example.acto_poc.data.database.entity.AlbumEntity
import com.example.acto_poc.data.database.entity.PhotoEntity
import com.example.acto_poc.data.database.entity.UserEntity

@Database(entities = [UserEntity::class,AlbumEntity::class,PhotoEntity::class],version = 1,exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun albumDao():AlbumDao
    abstract fun photoDao():PhotoDao


}