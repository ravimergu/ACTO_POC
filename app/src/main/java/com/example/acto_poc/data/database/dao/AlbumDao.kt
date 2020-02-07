package com.example.acto_poc.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acto_poc.data.database.entity.AlbumEntity
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(albumEntity: AlbumEntity):Maybe<Long>

    @Query("select * from albumentity where userId=:userId")
    fun getAlbumByUserId(userId:Int):Observable<List<AlbumEntity>>
}