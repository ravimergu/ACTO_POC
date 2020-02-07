package com.example.acto_poc.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.acto_poc.data.network.CustomObserver
import com.example.acto_poc.data.network.User
import com.example.acto_poc.data.repository.UserRepository
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val userLiveData: MutableLiveData<List<User>> = MutableLiveData()
    private var disposable: Disposable? = null

    fun getUserData(): MutableLiveData<List<User>> {
        return userLiveData
    }

    inner class UserObserver : CustomObserver<List<User>>() {

        override fun onSubscribe(d: Disposable) {
            disposable=d
        }

        override fun onNext(t: List<User>) {
            userLiveData.value=t
        }
    }

    fun loadUsers() {
        userRepository.subscribe(UserObserver())
    }



    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
