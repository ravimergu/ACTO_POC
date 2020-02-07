package com.example.acto_poc.data.repository

import android.annotation.SuppressLint
import com.example.acto_poc.data.database.dao.UserDao
import com.example.acto_poc.data.database.entity.UserEntity
import com.example.acto_poc.data.network.ApiService
import com.example.acto_poc.data.network.User
import io.reactivex.Maybe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class UserRepository(
    val apiService: ApiService,
    val userDao: UserDao
) {


    val userResult: BehaviorSubject<List<User>> = BehaviorSubject.create()

    fun subscribe(subscriber: Observer<List<User>>) {
        userResult.subscribe(subscriber)
        getUsers()
    }

    @SuppressLint("CheckResult")
    private fun getUsers() {
        apiService.getUsers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t: List<User>? ->
                    if (t != null) userResult.onNext(t) else userResult.onNext(
                        listOf()
                    )
                },
                { t: Throwable? ->
                    userResult.onNext(listOf())
                    getUsersLocal()
                },
                { getUsersLocal() }
            )
    }

    @SuppressLint("CheckResult")
    private fun getUsersLocal() {
        userDao.getAllUsers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t: List<UserEntity>? -> checkLocalData(t) },
                { t: Throwable? -> checkLocalData(listOf()) }
            )
    }

    private fun checkLocalData(t: List<UserEntity>?) {
        if (t == null) return

        val value = userResult.value
        val newList = t.map { userEntity ->
            User(
                userEntity.id,
                userEntity.name,
                userEntity.userName,
                userEntity.email,
                userEntity.phone,
                userEntity.website
            )
        }
        if (value == null || value.isEmpty()) userResult.onNext(newList)
    }

    fun insertUser(userEntity: UserEntity): Maybe<Long> {
        return userDao.insertUser(userEntity)
    }
}