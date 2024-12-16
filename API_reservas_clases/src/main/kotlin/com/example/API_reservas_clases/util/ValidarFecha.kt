package com.example.API_reservas_clases.util

import com.example.API_reservas_clases.model.Reserva
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun validarFecha(reserva: Reserva): Boolean {
    // Verificar si se puede eliminar basándonos en la fecha de la clase
    val clase = reserva.clase
    val fechaClase = clase?.fecha_clase
    val fechaHoraActual = LocalDateTime.now()

    // Calcular la diferencia entre la fecha actual y la fecha de la clase
    val horasRestantes = ChronoUnit.HOURS.between(fechaHoraActual, fechaClase)

    // Si quedan menos de 2 horas, no se puede eliminar la reserva
    if (horasRestantes < 2) {
        throw IllegalArgumentException("No se puede eliminar la reserva, quedan menos de 2 horas para la clase.")
    } else {
        return true
    }
}