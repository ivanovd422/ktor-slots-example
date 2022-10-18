package com.example.features.registration

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Routing.registrationRouting() {

//    val unauthenticatedController by inject<RegistrationController>()

    post("register") {
        val postUser = call.receive<RegisterUserRequest>()
        val user = unauthenticatedController.createUser(postUser)
        call.respond(user)
    }

//    post("authenticate") {
//        val credentials = call.receive<LoginCredentials>()
//        val loginTokenResponse = unauthenticatedController.authenticate(credentials)
//        call.respond(loginTokenResponse)
//    }
//
//    post("token") {
//        val credentials = call.receive<RefreshBody>()
//        val credentialsResponse = unauthenticatedController.refreshToken(credentials)
//        call.respond(credentialsResponse)
//    }
}