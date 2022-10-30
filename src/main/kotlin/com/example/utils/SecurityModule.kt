package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.interfaces.JWTVerifier
import com.example.db.DatabaseProviderContract
import com.example.features.registration.RegistrationApi
import io.ktor.server.application.*

fun Application.configureSecurity(
    registrationApi: RegistrationApi,
    databaseProvider: DatabaseProviderContract,
    tokenVerifier: JWTVerifier
) {

    authentication {
        jwt("jwt") {
            verifier(tokenVerifier)
            realm = "ktor.io"
            validate {
                it.payload.getClaim("id").asInt()?.let { userId ->
                    // do database query to find Principal subclass
                    databaseProvider.dbQuery {
                        registrationApi.getUserById(userId)
                    }
                }
            }
        }
    }

}


