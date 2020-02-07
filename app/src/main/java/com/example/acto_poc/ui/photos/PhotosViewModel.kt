package com.example.acto_poc.ui.photos

import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.acto_poc.data.database.entity.AlbumEntity
import com.example.acto_poc.data.database.entity.PhotoEntity
import com.example.acto_poc.data.database.entity.UserEntity
import com.example.acto_poc.data.network.Album
import com.example.acto_poc.data.network.CustomObserver
import com.example.acto_poc.data.network.Photo
import com.example.acto_poc.data.network.User
import com.example.acto_poc.data.repository.AlbumRepository
import com.example.acto_poc.data.repository.PhotoRepository
import com.example.acto_poc.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Response
import java.io.File
import java.util.*
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val albumRepository: AlbumRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {
    private lateinit var user: User
    private lateinit var album: Album
    private val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getPhotos(): MutableLiveData<List<Photo>> {
        return photosLiveData
    }

    fun setAlbum(album: Album) {
        this.album = album
    }

    fun setUser(user: User) {
        this.user = user
    }

    inner class PhotoObserver : CustomObserver<List<Photo>>() {
        override fun onSubscribe(d: Disposable) {
            compositeDisposable.add(d)
        }

        override fun onNext(t: List<Photo>) {
            photosLiveData.value = t
        }


    }

    fun loadPhotos() {
        photoRepository.subscribe(PhotoObserver(), album.id)
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun downloadImage(photo: Photo) {
        if(photo.url == null) return
        compositeDisposable.add(photoRepository.downloadImage(photo.url)
            .flatMap(Function<Response<ResponseBody>, Observable<File>> { t: Response<ResponseBody> ->
                val fileName = UUID.randomUUID().toString() + ".jpg"
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    fileName
                )
                file.createNewFile()
                val buffer = Okio.buffer(Okio.sink(file))
                buffer.writeAll(t.body()?.source())
                buffer.close()
                return@Function Observable.just(file)
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("file", it.absolutePath)
                    photoRepository.photoDao.insert(
                        PhotoEntity(
                            photo.id,
                            photo.albumId,
                            photo.title,
                            it.absolutePath,
                            it.absolutePath
                        )
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {t: Long? -> Log.d("success Insert photo","id : "+t) },
                            {t: Throwable? -> Log.e("error Insert photo","error :"+ t?.message) }
                        )
                    albumRepository.insertAlbum(
                        AlbumEntity(
                            album.id,
                            album.userId,
                            album.title
                        )
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {t: Long? -> Log.d("success Insert album","id : "+t) },
                            {t: Throwable? -> Log.e("error Insert album","error :"+ t?.message) }
                        )
                    userRepository.insertUser(
                        UserEntity(
                            user.id,
                            user.name,
                            user.userName,
                            user.email,
                            user.phone,
                            user.website
                        )
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {t: Long? -> Log.d("success Insert user","id : "+t) },
                            {t: Throwable? -> Log.e("error Insert user","error :"+t?.message) }
                        )

                    val photosList = photosLiveData.value
                    val list: List<Photo>? = photosList?.map { p ->
                        if (p.id == photo.id) {
                            return@map Photo(
                                photo.albumId,
                                photo.id,
                                photo.title,
                                it.absolutePath,
                                photo.thumbnailUrl,
                                true
                            )
                        }
                        return@map p
                    }

                    photosLiveData.value = list
                },
                { t: Throwable? -> Log.e("error ", "message " + t?.message) },
                { Log.d("complete", "complete") }
            ))
    }
}



