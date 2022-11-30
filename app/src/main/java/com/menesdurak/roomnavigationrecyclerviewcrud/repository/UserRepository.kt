package com.menesdurak.roomnavigationrecyclerviewcrud.repository

import androidx.lifecycle.LiveData
import com.menesdurak.roomnavigationrecyclerviewcrud.data.UserDao
import com.menesdurak.roomnavigationrecyclerviewcrud.fragments.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}