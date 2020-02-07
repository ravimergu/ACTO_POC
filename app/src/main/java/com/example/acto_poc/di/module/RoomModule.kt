package com.example.acto_poc.di.module

import android.app.Application
import androidx.room.Room
import com.example.acto_poc.MainApp
import com.example.acto_poc.data.database.RoomDB
import com.example.acto_poc.data.database.dao.AlbumDao
import com.example.acto_poc.data.database.dao.PhotoDao
import com.example.acto_poc.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDB(mainApp: MainApp): RoomDB {
        return Room.databaseBuilder(mainApp,RoomDB::class.java,"RoomDB").build()
    }

    @Singleton
    @Provides
    fun provideUserDao(roomDB: RoomDB): UserDao {
        return roomDB.userDao()
    }

    @Singleton
    @Provides
    fun providePhotoDao(roomDB: RoomDB): PhotoDao {
        return roomDB.photoDao()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(roomDB: RoomDB): AlbumDao {
        return roomDB.albumDao()
    }
}