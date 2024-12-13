package com.example.API_reservas_clases

import com.example.API_reservas_clases.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class ApiReservasClasesApplication

fun main(args: Array<String>) {
	runApplication<ApiReservasClasesApplication>(*args)
}
