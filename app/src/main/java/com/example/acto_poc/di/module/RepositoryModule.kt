package com.example.acto_poc.di.module

import com.example.acto_poc.data.database.dao.AlbumDao
import com.example.acto_poc.data.database.dao.PhotoDao
import com.example.acto_poc.data.database.dao.UserDao
import com.example.acto_poc.data.network.ApiService
import com.example.acto_poc.data.repository.AlbumRepository
import com.example.acto_poc.data.repository.PhotoRepository
import com.example.acto_poc.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {


    @Singleton
    @Provides
    fun provideUserRepository(apiService: ApiService, userDao: UserDao): UserRepository {
        return UserRepository(apiService, userDao)
    }

    @Singleton
    @Provides
    fun provideAlbumRepository(apiService: ApiService, albumDao: AlbumDao): AlbumRepository {
        return AlbumRepository(apiService, albumDao)
    }

    @Singleton
    @Provides
    fun providePhotoRepository(apiService: ApiService, photoDao: PhotoDao): PhotoRepository {
        return PhotoRepository(apiService, photoDao)
    }
}