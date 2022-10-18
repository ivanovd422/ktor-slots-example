package com.example.features.registration.models

import io.ktor.server.auth.*

data class User(
    val id: Int,
    val name: String,
    val secondname: String,
    val username: String,
    val password: String
) : Principal

data class PostUserBody(
    val name: String,
    val secondname: String,
    val username: String,
    val password: String
) : Principal

data class PutUserBody(
    val name: String? = null,
    val secondname: String? = null,
    val username: String? = null
) : Principal