package com.example.features.registration

import com.example.features.registration.models.PostUserBody
import com.example.features.registration.models.User
import com.example.utils.PasswordManager
import org.koin.core.KoinComponent
import org.koin.core.inject

interface RegistrationApi {
        fun getUserById(id: Int): User?
    fun getUserByUsername(username: String): User?
//    fun updateUserProfile(userId: Int, putUserBody: PutUserBody): User?
        fun removeUser(userId: Int): Boolean
    fun createUser(postUser: PostUserBody): User?
}


class RegistrationApiImpl(): KoinComponent, RegistrationApi {


    private val usersDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManager>()


}