package com.example.features.registration

import com.example.base.BaseController
import com.example.features.registration.models.PostUserBody
import com.example.features.registration.models.ResponseUser
import com.example.features.registration.models.toResponseUser
import com.example.statuspages.InvalidUserException
import com.example.utils.PasswordManager
import com.example.utils.TokenProvider
import org.koin.core.component.inject

interface RegistrationController {
    suspend fun createUser(postUser: PostUserBody): ResponseUser
}

class RegistrationControllerImpl() : BaseController(),  RegistrationController {

    private val userApi by inject<RegistrationApi>()
    private val passwordManager by inject<PasswordManager>()
    private val tokenProvider by inject<TokenProvider>()

    override suspend fun createUser(postUser: PostUserBody): ResponseUser {
        val user = dbQuery {
            userApi.getUserByUsername(postUser.username)?.let {
                throw InvalidUserException("User is already taken")
            }
            userApi.createUser(postUser) ?: throw UnknownError("Internal server error")
        }
        return user.toResponseUser()
    }
}