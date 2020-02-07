package com.example.acto_poc.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.acto_poc.ui.albums.AlbumViewModel
import com.example.acto_poc.ui.photos.PhotosViewModel
import com.example.acto_poc.ui.user.UserViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindThemeViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel::class)
    abstract fun bindLegoSetsViewModel(viewModel: AlbumViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    abstract fun bindLegoSetViewModel(viewModel: PhotosViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
