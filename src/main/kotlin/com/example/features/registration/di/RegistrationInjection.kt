package com.example.features.registration.di

import com.example.db.dao.UserDao
import com.example.db.dao.Users
import com.example.features.registration.RegistrationApi
import com.example.features.registration.RegistrationApiImpl
import com.example.features.registration.RegistrationController
import com.example.features.registration.RegistrationControllerImpl
import org.koin.dsl.module


object RegistrationInjection {
    val koinBeans = module {
        single<RegistrationApi> { RegistrationApiImpl() }
        single<UserDao> { Users }
        single<RegistrationController> { RegistrationControllerImpl() }
//        single<UserController> { UserControllerImp() }
    }
}