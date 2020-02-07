package com.example.acto_poc.ui.albums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.acto_poc.data.network.Album
import com.example.acto_poc.data.network.CustomObserver
import com.example.acto_poc.data.network.User
import com.example.acto_poc.data.repository.AlbumRepository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class AlbumViewModel @Inject constructor(private val albumRepository: AlbumRepository) :
    ViewModel() {
    private var user: User? = null
    private var disposable: Disposable? = null

    private val albums: MutableLiveData<List<Album>> = MutableLiveData()
    fun setUser(user: User?) {
        this.user = user
    }

    fun getAlbums(): MutableLiveData<List<Album>> {
        return albums
    }


    inner class AlbumObserver : CustomObserver<List<Album>>() {
        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onNext(t: List<Album>) {
            albums.value = t
        }
    }

    fun loadAlbums() {
        albumRepository.subscribe(AlbumObserver(), user?.id)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }


}
