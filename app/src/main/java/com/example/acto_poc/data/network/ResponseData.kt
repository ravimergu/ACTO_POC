package com.example.acto_poc.data.network

import android.os.Parcelable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.parcel.Parcelize

open class CustomObserver<T>:Observer<T>{
    override fun onComplete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSubscribe(d: Disposable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNext(t: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(e: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

@Parcelize
data class Photo(
    val albumId: Int,
    val id: Int,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?,
    val isDownloaded:Boolean=false
) : Parcelable

@Parcelize
data class Album(
    val userId: Int,
    val id: Int,
    val title: String
) : Parcelable

@Parcelize
data class User(
    val id: Int,
    val name: String?,
    val userName: String?,
    val email: String?,
    val phone: String?,
    val website: String?
) : Parcelable

@Parcelize
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val geo: Geo
) : Parcelable

@Parcelize
data class Geo(
    val lat: String,
    val lng: String
) : Parcelable

@Parcelize
data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
) : Parcelable
