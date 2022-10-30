package com.example.statuspages

data class InvalidUserException(override val message: String = "Invalid user") : Exception()