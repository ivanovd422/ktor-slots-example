package com.example.features.registration

import com.example.db.dao.UserDao
import com.example.features.registration.models.PostUserBody
import com.example.features.registration.models.User
import com.example.statuspages.InvalidUserException
import com.example.utils.PasswordManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface RegistrationApi {
    fun getUserById(id: Int): User?
    fun getUserByUsername(username: String): User?

    //    fun updateUserProfile(userId: Int, putUserBody: PutUserBody): User?
    fun removeUser(userId: Int): Boolean
    fun createUser(postUser: PostUserBody): User?
}


class RegistrationApiImpl() : KoinComponent, RegistrationApi {


    private val usersDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManager>()

    override fun getUserById(id: Int): User? {
        return usersDao.getUserById(id)
    }

    override fun getUserByUsername(username: String): User? {
        return usersDao.getUserByName(username)
    }

    override fun removeUser(userId: Int): Boolean {
        return usersDao.deleteUser(userId)
    }

    override fun createUser(postUser: PostUserBody): User? {
        val encryptedUser = postUser.copy(password = passwordEncryption.encryptPassword(postUser.password))
        val key: Int? = usersDao.insertUser(encryptedUser)
        return key?.let {
            usersDao.getUserById(it)
        } ?: throw InvalidUserException("Error while creating user")
    }
}