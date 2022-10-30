package com.example.statuspages

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }

        exception<InvalidUserException> { call, cause  ->
            call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
        }
    }

}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()