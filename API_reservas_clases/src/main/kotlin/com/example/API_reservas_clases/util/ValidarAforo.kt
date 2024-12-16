package com.example.API_reservas_clases.util

import org.springframework.security.access.AccessDeniedException

fun validarAforo(reservas: Int, aforo: Int) {
    if (reservas >= aforo) {
        throw AccessDeniedException("La clase ya ha alcanzado su aforo máximo.")
    }
}