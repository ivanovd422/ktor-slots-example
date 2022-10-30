package com.example.features.registration

import com.example.features.registration.models.PostUserBody
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Routing.registrationRouting() {


    val unauthenticatedController by inject<RegistrationController>()

    post("user") {
        val postUser = call.receive<PostUserBody>()
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