package com.example

import com.auth0.jwt.interfaces.JWTVerifier
import com.example.db.DatabaseProviderContract
import com.example.features.registration.RegistrationApi
import com.example.features.registration.models.User
import com.example.features.registration.registrationRouting
import com.example.plugins.configureSecurity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.koin.core.component.inject
import org.koin.ktor.ext.inject
import org.slf4j.event.Level

fun Application.module() {

    val userApi by inject<RegistrationApi>()
    val databaseProvider by inject<DatabaseProviderContract>()
    val jwtVerifier by inject<JWTVerifier>()
    //Init database here
    databaseProvider.init()

    install(CallLogging) {
        level = Level.DEBUG
    }
    install(ContentNegotiation) { gson { } }
    install(StatusPages) {
        generalStatusPages()
        userStatusPages()
        authStatusPages()
        exception<UnknownError> {
            call.respondText(
                "Internal server error",
                ContentType.Text.Plain,
                status = HttpStatusCode.InternalServerError
            )
        }
        exception<IllegalArgumentException> {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    configureSecurity(userApi, databaseProvider, jwtVerifier)

    install(Routing) {
        static("/static") {
            resources("static")
        }

        registrationRouting()
        authenticate("jwt") {
            //todo add here routing under jwt
//            registrationRouting()
        }
    }
}

val ApplicationCall.user get() = authentication.principal<User>()!!

suspend fun PipelineContext<Unit, ApplicationCall>.sendOk() {
    call.respond(HttpStatusCode.OK)
}