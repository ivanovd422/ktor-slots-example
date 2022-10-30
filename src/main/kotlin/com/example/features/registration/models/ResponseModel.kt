package com.example.features.registration.models

data class ResponseUser(
    val id: Int,
    val name: String,
    val secondname: String,
    val username: String
)

fun User.toResponseUser() = ResponseUser(
    this.id,
    this.name,
    this.secondname,
    this.username
)