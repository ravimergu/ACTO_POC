package com.example.acto_poc.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.acto_poc.data.database.entity.UserEntity
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity):Maybe<Long>

    @Query("Select * from userentity")
    fun getAllUsers():Observable<List<UserEntity>>
}