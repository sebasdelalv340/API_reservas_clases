package com.example.API_reservas_clases.repository

import com.example.API_reservas_clases.model.Clase
import com.example.API_reservas_clases.model.Reserva
import org.springframework.data.jpa.repository.JpaRepository

interface ReservaRepository : JpaRepository<Reserva, Long>{

    // Contar el número de reservas para una clase
    fun countByClase(clase: Clase): Long
}