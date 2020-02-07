package com.example.acto_poc.di

import com.example.acto_poc.di.module.ApiModule
import com.example.acto_poc.di.module.AppModule
import com.example.acto_poc.di.module.RepositoryModule
import com.example.acto_poc.di.module.RoomModule
import com.example.acto_poc.ui.albums.AlbumFragment
import com.example.acto_poc.ui.photos.PhotosFragment
import com.example.acto_poc.ui.user.UserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, ViewModelModule::class, RoomModule::class,RepositoryModule::class])
interface AppComponent {
    fun inject(userFragment: UserFragment)
    fun inject(albumFragment: AlbumFragment)
    fun inject(photosFragment: PhotosFragment)

}