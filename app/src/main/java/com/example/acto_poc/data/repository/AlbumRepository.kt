package com.example.acto_poc.data.repository

import android.annotation.SuppressLint
import com.example.acto_poc.data.database.dao.AlbumDao
import com.example.acto_poc.data.database.entity.AlbumEntity
import com.example.acto_poc.data.network.Album
import com.example.acto_poc.data.network.ApiService
import io.reactivex.Maybe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class AlbumRepository(
    private val apiService: ApiService,
    private val albumDao: AlbumDao
) {

    private var userId: Int = 0

    private val albumResult: BehaviorSubject<List<Album>> = BehaviorSubject.create()

    fun subscribe(subscriber: Observer<List<Album>>, userId: Int?) {
        if (userId != null) this.userId=userId
        albumResult.subscribe(subscriber)
        getAlbums()
    }
    @SuppressLint("CheckResult")
    private fun getAlbums(){
         apiService.getAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
             .map { t: List<Album> -> t.filter { album -> album.userId== userId } }
            .subscribe(
                {t: List<Album>? -> if(t==null) albumResult.onNext(listOf()) else albumResult.onNext(t) },
                {t: Throwable? ->
                    albumResult.onNext(listOf())
                    getAlbumLocal(userId)
                },
                {getAlbumLocal(userId)}
            );
    }
    @SuppressLint("CheckResult")
    fun getAlbumLocal(userId:Int) {
        albumDao.getAlbumByUserId(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t: List<AlbumEntity>? -> checkWithLocal(t) }
    }

    private fun checkWithLocal(t: List<AlbumEntity>?) {
        if(t==null) return
        val value = albumResult.value
        val newList=t.map { albumEntity: AlbumEntity -> Album(albumEntity.userId,albumEntity.id,albumEntity.title) }
        if(value==null||value.isEmpty()) albumResult.onNext(newList)
    }

    fun insertAlbum(albumEntity: AlbumEntity): Maybe<Long> {
        return albumDao.insert(albumEntity)
    }
}