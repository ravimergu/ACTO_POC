package com.example.acto_poc.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acto_poc.data.database.entity.PhotoEntity
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photoEntity: PhotoEntity): Maybe<Long>

    @Query("select * from photoentity where albumId=:albumId")
    fun getPhotoByAlbumId(albumId:Int): Observable<List<PhotoEntity>>
}