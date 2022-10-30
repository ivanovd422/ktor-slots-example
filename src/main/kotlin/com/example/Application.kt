package com.example

import com.auth0.jwt.interfaces.JWTVerifier
import com.example.base.Config
import com.example.db.DatabaseProvider
import com.example.db.DatabaseProviderContract
import com.example.features.registration.di.RegistrationInjection
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.utils.JwtConfig
import com.example.utils.PasswordManager
import com.example.utils.PasswordManagerImpl
import com.example.utils.TokenProvider
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.util.*
import javafx.application.Application
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun main() {

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
//        configureSecurity()
        configureSerialization()
        configureStatusPage()
        configureMonitoring()
        configureRouting()
    }.start(wait = true)

    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    val config = extractConfig(environment, HoconApplicationConfig(ConfigFactory.load()))

    embeddedServer(Netty, port = config.port) {
        println("Starting instance in ${config.host}:${config.port}")
        module {
            install(Koin) {
                modules(
                    module {
                        single { config }
                        single<DatabaseProviderContract> { DatabaseProvider() }
                        single<JWTVerifier> { JwtConfig.verifier }
                        single<PasswordManager> { PasswordManagerImpl() }
                        single<TokenProvider> { JwtConfig }
                    },
                    RegistrationInjection.koinBeans,
                )
            }
            main()
        }
    }.start(wait = true)
}


fun handleDefaultEnvironment(): String {
    println("Falling back to default environment 'dev'")
    return "dev"
}

fun Application.main() {
    module()
}

@KtorExperimentalAPI
fun extractConfig(environment: String, hoconConfig: HoconApplicationConfig): Config {
    val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
    return Config(
        hoconEnvironment.property("host").getString(),
        Integer.parseInt(hoconEnvironment.property("port").getString()),
        hoconEnvironment.property("databaseHost").getString(),
        hoconEnvironment.property("databasePort").getString()
    )
}
