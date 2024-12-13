package com.example.API_reservas_clases.util

import java.time.LocalDate

fun validarReserva(reservaFecha: LocalDate, claseFecha: LocalDate): Boolean {
    if (reservaFecha.isAfter(claseFecha)) {
        throw IllegalArgumentException("La fecha de la reserva ($reservaFecha) no puede ser posterior a la fecha de la clase ($claseFecha).")
    }
    return true // Validación exitosa
}