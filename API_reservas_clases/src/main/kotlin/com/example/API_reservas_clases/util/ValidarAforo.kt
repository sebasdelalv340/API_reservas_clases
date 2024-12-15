package com.example.API_reservas_clases.util

fun validarAforo(reservas: Long, aforo: Int): Boolean {
    return reservas.toInt() != aforo
}