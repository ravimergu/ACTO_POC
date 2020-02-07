package com.example.acto_poc.data.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface ApiService {

    @GET(ApiURL.USERS)
    fun getUsers():Observable<List<User>>

    @GET(ApiURL.PHOTOS)
    fun getPhotos():Observable<List<Photo>>

    @GET(ApiURL.ALBUMS)
    fun getAlbums():Observable<List<Album>>

    @Streaming
    @GET
    fun getImageData(@Url url: String): Observable<Response<ResponseBody>>
}