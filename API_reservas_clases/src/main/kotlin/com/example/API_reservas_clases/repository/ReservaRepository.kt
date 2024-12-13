package com.example.API_reservas_clases.repository

import com.example.API_reservas_clases.model.Reserva
import org.springframework.data.jpa.repository.JpaRepository

interface ReservaRepository : JpaRepository<Reserva, Long>{}