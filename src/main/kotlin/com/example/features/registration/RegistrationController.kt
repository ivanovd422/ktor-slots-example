package com.example.features.registration

import com.example.base.BaseController
import org.koin.core.inject

interface RegistrationController {
    suspend fun createUser(user: RegisterUserRequest): RegisterUserResponse
}

class RegistrationControllerImpl() : BaseController(),  RegistrationController {

    private val registrationApi by inject<RegistrationApi>()
//    private val passwordManager by inject<PasswordManagerContract>()
//    private val tokenProvider by inject<TokenProvider>()

    override suspend fun createUser(user: RegisterUserRequest): RegisterUserResponse {
        val user = dbQuery {
            registrationApi.getUserByUsername(user.login)?.let {
                throw InvalidUserException("User is already taken")
            }
            registrationApi.createUser(postUser) ?: throw UnknownError("Internal server error")
        }
        return user.toResponseUser()

        return RegisterUserResponse("123", "132", "")
    }
}