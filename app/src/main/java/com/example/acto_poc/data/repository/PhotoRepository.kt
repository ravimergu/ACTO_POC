package com.example.acto_poc.data.repository

import android.annotation.SuppressLint
import com.example.acto_poc.data.database.dao.PhotoDao
import com.example.acto_poc.data.database.entity.PhotoEntity
import com.example.acto_poc.data.network.ApiService
import com.example.acto_poc.data.network.Photo
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import okhttp3.ResponseBody
import retrofit2.Response

class PhotoRepository(
    private val apiService: ApiService,
    val photoDao: PhotoDao
) {

    private var albumId: Int=0
    private val photoResult: BehaviorSubject<List<Photo>> = BehaviorSubject.create()

    fun subscribe(subscriber: Observer<List<Photo>>, albumId: Int) {
        this.albumId=albumId
        photoResult.subscribe(subscriber)
        getPhotos()
    }
    @SuppressLint("CheckResult")
    fun getPhotos() {
        apiService.getPhotos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t: List<Photo> -> t.filter { photo -> photo.albumId== albumId } }
            .subscribe(
                {t: List<Photo>? -> if(t==null) photoResult.onNext(listOf()) else photoResult.onNext(t) },
                {t: Throwable? ->
                    photoResult.onNext(listOf())
                    getPhotosLocal(albumId)
                },
                {getPhotosLocal(albumId)}
            );
    }

    @SuppressLint("CheckResult")
    fun getPhotosLocal(albumId:Int) {
         photoDao.getPhotoByAlbumId(albumId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t: List<PhotoEntity>? -> checkWithLocal(t) }
    }

    private fun checkWithLocal(t: List<PhotoEntity>?) {
        if(t==null) return
        val value = photoResult.value
        val newList=t.map { photoEntity: PhotoEntity -> Photo(photoEntity.albumId,photoEntity.id,photoEntity.title,photoEntity.url,photoEntity.url,true) }
        if(value==null||value.isEmpty()) photoResult.onNext(newList)
        else
        {
            val list = value.map { photo: Photo ->
                for (localPhoto in newList) {
                    if (localPhoto.id == photo.id) {
                        return@map Photo(
                            photo.albumId,
                            photo.id,
                            photo.title,
                            localPhoto.url,
                            localPhoto.url,
                            true
                        )
                    }
                }
                return@map photo
            }
            photoResult.onNext(list)
        }
    }

    fun insertPhoto(photoEntity: PhotoEntity): Maybe<Long> {
        return photoDao.insert(photoEntity)
    }
    fun downloadImage(url:String): Observable<Response<ResponseBody>> {
        return apiService.getImageData(url)
    }
}